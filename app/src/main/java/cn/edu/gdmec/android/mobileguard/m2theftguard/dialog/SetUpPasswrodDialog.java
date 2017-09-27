package cn.edu.gdmec.android.mobileguard.m2theftguard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by 通哥 on 2017/9/26.
 */

public class SetUpPasswrodDialog extends Dialog implements View.OnClickListener {

    private TextView mTitleTV;
    public  EditText mFirstPWDET;
    public  EditText mAffirmET;
    private MyCallBack myCallBack;


    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.btn_ok:
               myCallBack.ok();
               break;
           case R.id.btn_cancel:
               myCallBack.cancel();
               break;
       }
    }

    public SetUpPasswrodDialog(@NonNull Context context){
        super(context, R.style.dialog_custom);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.setup_password_dialog);
        super.onCreate(savedInstanceState);
        initView();
    }

    /*
    初始化方法
     */
    public void initView(){
        mTitleTV=(TextView)findViewById(R.id.tv_setuppwd_title);
        mFirstPWDET=(EditText)findViewById(R.id.et_firstpwd);
        mAffirmET=(EditText)findViewById(R.id.et_affirm_password);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    /*
    设置对话框标题
     */
    public void setTitle(String Title){
        if(!TextUtils.isEmpty(Title)){
            mTitleTV.setText(Title);
        }
    }

    public void setMyCallBack(MyCallBack myCallBack){
        this.myCallBack=myCallBack;
    }

    public interface MyCallBack{
        void ok();
        void cancel();
    }
}
