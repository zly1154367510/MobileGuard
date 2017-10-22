package cn.edu.gdmec.android.mobileguard.m2theftguard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by zly11 on 2017/10/13.
 */

public class Setup2Activity  extends BaseSetUpActivity implements View.OnClickListener {

    private TelephonyManager mTelephoneManager;
    private Button mBindSIMBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_2);
        ((RadioButton)findViewById(R.id.rb_second)).setChecked(true);

        mBindSIMBtn=(Button)findViewById(R.id.btn_bind_sim);
        mBindSIMBtn.setOnClickListener(this);
        mTelephoneManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (isBind()){
            mBindSIMBtn.setEnabled(false);
        }else{
            mBindSIMBtn.setEnabled(true);
        }
    }

    @Override
    public void showNext() {

        if (!isBind()) {
            Toast.makeText(this,"还没有绑定sim卡",Toast.LENGTH_SHORT);

        }else{

            startActivityFinishSelf(Setup3Activity.class);
        }
    }

    @Override
    public void showPre() {
        startActivityFinishSelf (Setup1Activity.class);
    }

    @Override
    public void onClick(View v) {
          switch (v.getId()){
              case R.id.btn_bind_sim :
                  //绑定smi卡方法
                  bindSim();
                  break;
          }
    }

    public boolean isBind(){
        String simStr=sp.getString("sim",null);
        if (TextUtils.isEmpty(simStr)){
            return false;
        }
        return true;
    }

    public void bindBtnShow(){
        if (isBind()){
            mBindSIMBtn.setEnabled(false);
        }else{
            mBindSIMBtn.setEnabled(true);
        }
    }

    public void bindSim(){
        if (!isBind()){
            String simSerialNumber = mTelephoneManager.getSimSerialNumber();
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("sim",simSerialNumber);
            editor.commit();
            Toast.makeText(this,"绑定成功",Toast.LENGTH_SHORT).show();
            mBindSIMBtn.setEnabled(false);
        }else{
            String simStr = sp.getString("sim",null);
            Toast.makeText(this,simStr,Toast.LENGTH_SHORT).show();
            mBindSIMBtn.setEnabled(false);
        }
    }
}
