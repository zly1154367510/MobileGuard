package cn.edu.gdmec.android.mobileguard.m2theftguard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by zly11 on 2017/9/27.
 */

public class InterPasswordDialog extends Dialog implements View.OnClickListener{

    public TextView mTitleTV;
    public EditText mInterET;
    public Button mOkBtn;
    public Button mCancleBtn;
    public  MyCallBack myCallBack;
    public Context context;

    public InterPasswordDialog (@NonNull Context context){
        super(context,R.style.dialog_custom);
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.inter_password_dialog);
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void onClick(View v) {
          switch (v.getId()){
              case R.id.btn_comfirm:
                  myCallBack.ok();
                  break;
              case R.id.btn_dismiss:
                  myCallBack.cancel();
                  break;
          }
    }
    /*
    初始化View
     */
    public void initView(){
        mTitleTV=(TextView)findViewById(R.id.tv_interpwd_title);
        mOkBtn=(Button)findViewById(R.id.btn_comfirm);
        mCancleBtn=(Button)findViewById(R.id.btn_dismiss);
        mInterET=(EditText)findViewById(R.id.et_inter_password);
        mOkBtn.setOnClickListener(this);
        mCancleBtn.setOnClickListener(this);
    }

    /*
    回调方法
     */
    public void setMyCallBack(MyCallBack myCallBack){
        this.myCallBack=myCallBack;
    }

    /*
    回调接口
     */
    public interface MyCallBack{
        void ok();
        void cancel();
    }
    /*
    获取输入框密码
    return string
     */
    public String getPassword(){
        return mInterET.getText().toString();
    }
}
