package cn.edu.gdmec.android.mobileguard.m5virusscan;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m5virusscan.adapter.ScanVirusAdapter;
import cn.edu.gdmec.android.mobileguard.m5virusscan.entity.ScanAppInfo;

public class VirusScanActivity extends AppCompatActivity {

    protected static final int SCAN_BENGIN = 100;
    protected static final int SCANNING = 101;
    protected static final int SCAN_FINISH = 102;
    private int total;
    private int process;
    private TextView mProcessTV;
    private PackageManager pm;
    private boolean flag;
    private boolean isStop;
    private TextView mScanAppTV;
    private ImageView mSanningIcon;
    private Button mCancleBtn;
    private RotateAnimation rani;
    private ListView mScanListView;
    private ScanVirusAdapter adapter;
    private List<ScanAppInfo> mScanAppInfos = new ArrayList<ScanAppInfo>();
    private SharedPreferences mSP;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case SCAN_BENGIN:
                    mScanAppTV.setText("初始化杀毒引擎中");
                    break;
                case SCANNING:
                    ScanAppInfo info = (ScanAppInfo)msg.obj;
                    mScanAppTV.setText("正在扫描:"+info.appName);
                    int speed = msg.arg1;
                    mProcessTV.setText((speed*100/total)+"%");
                    mScanAppInfos.add(info);
                    adapter.notifyDataSetChanged();
                    mScanListView.setSelection(mScanAppInfos.size());
                    break;
                case SCAN_FINISH:
                    mScanAppTV.setText("扫描完成");
                    mSanningIcon.clearAnimation();
                    mCancleBtn.setBackgroundResource(R.drawable.scan_complete);
                    saveScanTime();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virus_scan);
    }

    private void saveScanTime(){
        SharedPreferences.Editor editor = mSP.edit();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        currentTime = "上次查杀"+currentTime;
        editor.putString("lastVirusScan",currentTime);
        editor.commit();
    }


}
