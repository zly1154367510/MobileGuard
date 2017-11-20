package cn.edu.gdmec.android.mobileguard.m5virusscan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m2theftguard.utils.MD5Utils;
import cn.edu.gdmec.android.mobileguard.m5virusscan.adapter.ScanVirusAdapter;
import cn.edu.gdmec.android.mobileguard.m5virusscan.dao.AntiVirusDao;
import cn.edu.gdmec.android.mobileguard.m5virusscan.entity.ScanAppInfo;
import cn.edu.gdmec.android.mobileguard.m5virusscan.utils.VersionUpdate;

public class VirusScanActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mLastTimeTV;
    private SharedPreferences mSP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virus_scan);
        mSP = getSharedPreferences("config",MODE_PRIVATE);
        final VersionUpdate VU = new VersionUpdate();
        new Thread(){
            @Override
            public void run() {
                super.run();
                VU.getCloudVersion();
                Log.d("debug","进入");
            }
        }.start();
        copyDB("antivirus.db");
        initView();
    }

    @Override
    protected void onPostResume() {
        String string = mSP.getString("lastVirusScan","您还没有查杀病毒");
        mLastTimeTV.setText(string);
        super.onPostResume();

    }
    protected void copyDB(final String dbname){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
//                    File file = new File(getFilesDir(),dbname);
//                    if (file.exists()&&file.length()>0){
//                        Log.i("VirusSanActivity","数据库已存在");
//                        return;
//                    }
                    InputStream is = getAssets().open(dbname);
                    FileOutputStream fos = openFileOutput(dbname,MODE_PRIVATE);
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while((len = is.read())!= - 1){
                        fos.write(buffer,0,len);
                    }
                    is.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private  void initView(){
        ((TextView) findViewById(R.id.tv_title)).setText("病毒查杀");
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.purple));
        mLastTimeTV = (TextView)findViewById(R.id.tv_lastscantime);
        findViewById(R.id.rl_allscanvirus).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.rl_allscanvirus:
                startActivity(new Intent(this,VirusScanSpeedActivity.class));
                break;
        }

    }
}
