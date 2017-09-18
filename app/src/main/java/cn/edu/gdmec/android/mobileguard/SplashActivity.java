package cn.edu.gdmec.android.mobileguard;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cn.edu.gdmec.android.mobileguard.m1home.utils.MyUtils;
import cn.edu.gdmec.android.mobileguard.m1home.utils.VersionUpdateUtils;

public class SplashActivity extends AppCompatActivity {

    private String mVersion;
    private TextView mTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mTextview=(TextView)findViewById(R.id.tv_splash_version);
        mVersion= MyUtils.getVersion(getApplicationContext());
        mTextview.setText("版本号:"+mVersion);
        final VersionUpdateUtils versionUpdateUtils= new VersionUpdateUtils(mVersion,SplashActivity.this);
      new Thread(){
           @Override
            public void run(){
               super.run();
               versionUpdateUtils.getCloudVersion();
           }
        }.start();

}
}
