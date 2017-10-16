package cn.edu.gdmec.android.mobileguard.m2theftguard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m2theftguard.utils.BaseSetUpActivity;

/**
 * Created by zly11 on 2017/10/13.
 */

public class Setup2Activity  extends BaseSetUpActivity implements View.OnClickListener {

    private Button mButton;
    private TelephonyManager mTelephonyManager;
    private SharedPreferences sp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_2);
        ((RadioButton)findViewById(R.id.rb_first)).setChecked(true);
        mButton=(Button)findViewById(R.id.btn_sim_setup2);
        mButton.setOnClickListener(this);
        mTelephonyManager=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        sp=getSharedPreferences("config",MODE_PRIVATE);
        btnSetText();

    }

    @Override
    public void showNext() {
        if(inBend()){
            startActivityFinishSelf(Setup3Activity.class);
        }else{
            Toast.makeText(this,"还没绑定sim卡",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showPre() {
        startActivityFinishSelf (Setup2Activity.class);
    }

    @Override
    public void onClick(View v) {
        String simNum=mTelephonyManager.getSimSerialNumber();
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("simNum",simNum);
        editor.commit();
        Toast.makeText(this,simNum,Toast.LENGTH_LONG).show();

    }

    public boolean inBend(){
        String simNum=sp.getString("simNum","");
        if (simNum.equals("")){
            return false;
        }else{
            return true;
        }
    }

    public void btnSetText(){
        if (inBend()){
            mButton.setText("已绑定");
        }else{
            mButton.setText("未绑定");
        }
    }
}
