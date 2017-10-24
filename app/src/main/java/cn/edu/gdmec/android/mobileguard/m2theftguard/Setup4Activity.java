package cn.edu.gdmec.android.mobileguard.m2theftguard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by zly11 on 2017/10/13.
 */

public class Setup4Activity  extends BaseSetUpActivity {
    private TextView mStatusTV;
    private ToggleButton mToggleButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.edu.gdmec.android.mobileguard.R.layout.activity_setup_4);
        ((RadioButton)findViewById(cn.edu.gdmec.android.mobileguard.R.id.rb_four)).setChecked(true);
    }

    //初始化视图方法
    private void initView(){
        mStatusTV = (TextView)findViewById(R.id.tv_setup4_status);
        mToggleButton = (ToggleButton)findViewById(R.id.togglebtn_securityfunction);
        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            //监听状态改变TextView状态
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mStatusTV.setText("防盗保护已经开启");
                }else{
                    mStatusTV.setText("防盗保护没有开启");
                }
                //保存状态用以后面功能的验证
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("protecting",isChecked);
                editor.commit();
            }
        });
        boolean protecting = sp.getBoolean("protecting",true);
        if (protecting){
            mStatusTV.setText("防盗保护已经开启");
            mToggleButton.setChecked(true);
        }else{
            mStatusTV.setText("防盗保护没有开启");
            mToggleButton.setChecked(false);
        }

    }
    @Override
    public void showNext() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isSetUp",true);
        editor.commit();
        startActivityFinishSelf(LostFindActivity.class);


    }

    @Override
    public void showPre() {

        startActivityFinishSelf (Setup3Activity.class);
    }
}
