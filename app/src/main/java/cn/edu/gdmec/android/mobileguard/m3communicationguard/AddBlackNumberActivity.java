package cn.edu.gdmec.android.mobileguard.m3communicationguard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m2theftguard.ContactSelectActivity;
import cn.edu.gdmec.android.mobileguard.m3communicationguard.db.dao.BlackNumberDao;
import cn.edu.gdmec.android.mobileguard.m3communicationguard.entity.BlackContactInfo;

public class AddBlackNumberActivity extends AppCompatActivity implements View.OnClickListener{

    private CheckBox mSmsCB;
    private CheckBox mTelCB;
    private EditText mNumET;
    private EditText mNameET;
    //数据库操作类
    private BlackNumberDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_black_number);
        dao = new BlackNumberDao(AddBlackNumberActivity.this);
        init();
    }
   //联系人列表返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            String phone = data.getStringExtra("phone");
            String name = data.getStringExtra("name");
            mNameET.setText(name);
            mNumET.setText(phone);
          System.out.print(phone);
        }else{
            System.out.print("name");
        }
    }

    //初始化
    private void init(){
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_purple));
        ((TextView)findViewById(R.id.tv_title)).setText("添加黑名单");
        ImageView mLeftImgv = (ImageView)findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);

        mSmsCB = (CheckBox)findViewById(R.id.cb_blacknumber_sms);
        mTelCB = (CheckBox)findViewById(R.id.cb_blacknumber_tel);
        mNumET = (EditText)findViewById(R.id.et_balcknumber);
        mNameET = (EditText)findViewById(R.id.et_blackname);
        findViewById(R.id.add_blacknum_btn).setOnClickListener(this);
        findViewById(R.id.add_fromcontact_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.add_blacknum_btn:
                String phone = mNumET.getText().toString().trim();
                String name = mNameET.getText().toString().trim();
                if (TextUtils.isEmpty(phone)||TextUtils.isEmpty(name)){
                    Toast.makeText(this, "某项为空", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    BlackContactInfo info = new BlackContactInfo();
                    info.phoneNumber = phone;
                    info.contactName = name;
                    if (mSmsCB.isChecked()&mTelCB.isChecked()){
                        info.mode = 3;
                    }else if (mSmsCB.isChecked()& !mTelCB.isChecked()){
                        info.mode = 2;
                    }else if (!mSmsCB.isChecked() & mTelCB.isChecked()){
                        info.mode = 1;
                    }else {
                        Toast.makeText(this, "请选择拦截模式", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (dao.IsNumberExist(phone)){
                         dao.add(info);
                    }else{
                        Toast.makeText(this, phone, Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
                break;
            case R.id.add_fromcontact_btn:
                 startActivityForResult(new Intent(this,ContactSelectActivity.class),0);
                break;
        }
    }
}
