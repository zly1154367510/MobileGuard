package cn.edu.gdmec.android.mobileguard.m2theftguard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.w3c.dom.Text;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by zly11 on 2017/10/13.
 */

public class Setup3Activity  extends BaseSetUpActivity implements View.OnClickListener {

    private Button mSelectBtn;
    private EditText mInputPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.edu.gdmec.android.mobileguard.R.layout.activity_setup_3);
        ((RadioButton)findViewById(cn.edu.gdmec.android.mobileguard.R.id.rb_third)).setChecked(true);
        mSelectBtn = (Button)findViewById(R.id.btn_addcontact);
        mSelectBtn.setOnClickListener(this);
        mInputPhone=(EditText)findViewById(R.id.et_inputphone);
        String helpPhoneNum=sp.getString("help",null);
        if (!TextUtils.isEmpty(helpPhoneNum)){
            mInputPhone.setText(helpPhoneNum);
        }
    }

    @Override
    public void showNext() {


        String helpPhoneNum=mInputPhone.getText().toString().trim();
        if (TextUtils.isEmpty(helpPhoneNum)){
            Toast.makeText(this,"安全号码为空",Toast.LENGTH_LONG).show();
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("help",helpPhoneNum);
        editor.commit();
        startActivityFinishSelf (Setup4Activity.class);
    }

    @Override
    public void showPre() {

        startActivityFinishSelf (Setup2Activity.class);
    }

    @Override
    public void onClick(View v) {
          switch (v.getId()){
              case R.id.btn_addcontact:
                  startActivityForResult(new Intent(this,ContactSelectActivity.class),0);
                  break;
          }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            String phone = data.getStringExtra("phone");
            mInputPhone.setText(phone);
        }
    }
}
