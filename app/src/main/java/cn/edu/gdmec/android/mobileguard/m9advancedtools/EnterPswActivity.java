package cn.edu.gdmec.android.mobileguard.m9advancedtools;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard.App;
import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m2theftguard.utils.MD5Utils;

public class EnterPswActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView mAppIcon;
    private TextView mAppNameTV;
    private EditText mPswET;
    private ImageView mGoImgv;
    private LinearLayout mEnterPswLL;
    private SharedPreferences sp;
    private String password;
    private String packagename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_psw);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        password = sp.getString("PhoneAntiTheftPWD", null);
        Intent intent = getIntent();
        packagename = intent.getStringExtra("packagename");
        PackageManager pm = getPackageManager();
        initView();
        try {
            mAppIcon.setImageDrawable(pm.getApplicationInfo(packagename, 0).loadIcon(pm));
            mAppNameTV.setText(pm.getApplicationInfo(packagename, 0).loadLabel(pm).toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * 初始化控件
     */
    private void initView() {
        mAppIcon = (ImageView) findViewById(R.id.imgv_appicon_enterpsw);
        mAppNameTV = (TextView) findViewById(R.id.tv_appname_enterpsw);
        mPswET = (EditText) findViewById(R.id.et_psw_enterpsw);
        mGoImgv = (ImageView) findViewById(R.id.imgv_go_enterpsw);
        mEnterPswLL = (LinearLayout) findViewById(R.id.ll_enterpsw);
        mGoImgv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgv_go_enterpsw:
                //比较密码
                String inputpsw = mPswET.getText().toString().trim();
                if(TextUtils.isEmpty(inputpsw)){
                    startAnim();
                    Toast.makeText(this, "请输入密码！", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if(!TextUtils.isEmpty(password)){
                        if(MD5Utils.encode(inputpsw).equals(password)){
                            //发送自定义的广播消息。
                            Intent intent = new Intent();
                            intent.setAction(App.APPLOCK_ACTION);
                            intent.putExtra("packagename",packagename);
                            sendBroadcast(intent);
                            finish();
                        }else{
                            startAnim();
                            Toast.makeText(this, "密码不正确！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                break;
        }
    }
    private void startAnim() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
        mEnterPswLL.startAnimation(animation);
    }
}