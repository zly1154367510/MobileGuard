package cn.edu.gdmec.android.mobileguard.m4appmanager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import java.util.List;
import java.util.logging.Formatter;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m4appmanager.adapter.AppmanagerAdapter;
import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;
import cn.edu.gdmec.android.mobileguard.m4appmanager.utils.AppInfoParser;

public class AppManagerActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mPhoneMemoryTV;
    private TextView mSDMemoryTV;
    private ListView mListView;
    private List<AppInfo> appInfos;
    private List<AppInfo> userAppInfos = new ArrayList<AppInfo>();
    private List<AppInfo> systemAppInfos = new ArrayList<AppInfo>();
    private AppmanagerAdapter adapter;
    private TextView mAppNumTV;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    if (adapter == null) {
                        adapter = new AppmanagerAdapter(userAppInfos, systemAppInfos, AppManagerActivity.this);
                    }

                    mListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                case 15:
                    adapter.notifyDataSetChanged();
                    break;
            }
        }

    };

    private void initData(){
        appInfos = new ArrayList<AppInfo>();
        new Thread(){
            public void run() {
                appInfos.clear();
                userAppInfos.clear();
                systemAppInfos.clear();
                appInfos.addAll(AppInfoParser.getAppInfos(AppManagerActivity.this));
                for (AppInfo appInfo : appInfos){
                    if (appInfo.isUserApp){
                        userAppInfos.add(appInfo);
                    }else{
                        systemAppInfos.add(appInfo);
                    }
                }
                handler.sendEmptyMessage(10);
            };

        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        //IntentFilter intentFilter = new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        initView();
    }

    private void initView(){
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_purple));
        ((TextView)findViewById(R.id.tv_title)).setText("软件管家");
        ImageView mLeftImgv = (ImageView)findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        mPhoneMemoryTV = (TextView)findViewById(R.id.tv_phonememory_appmanager);
        mSDMemoryTV = (TextView)findViewById(R.id.tv_sdmemory_appmanager);
        mAppNumTV = (TextView)findViewById(R.id.tv_appnumber);
        mListView = (ListView) findViewById(R.id.lv_appmanager);
        initData();
        getMemoryFromPhone();
        initListener();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
        }
    }

    private void getMemoryFromPhone(){
        long avail_sd = Environment.getExternalStorageDirectory().getFreeSpace();
        long avail_rom = Environment.getDataDirectory().getFreeSpace();
        String str_avail_sd = "手机内存剩余33.8G";
        String str_rom_sd = "内存卡剩余15.8G";
        mPhoneMemoryTV.setText(str_avail_sd);
        mSDMemoryTV.setText(str_rom_sd);
    }

    private void initListener(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(adapter != null){
                     new Thread() {
                        @Override
                        public void run() {
                            AppInfo mappInfo = (AppInfo) adapter.getItem(position);
                            boolean flag = mappInfo.isSelected;
                            for (AppInfo appInfo : userAppInfos){
                                appInfo.isSelected = false;
                            }
                            for (AppInfo appInfo : systemAppInfos){
                                appInfo.isSelected = false;
                            }
                            if (mappInfo != null){
                                if (flag){
                                    mappInfo.isSelected = false;
                                }else{
                                    mappInfo.isSelected = true;
                                }
                                handler.sendEmptyMessage(15);
                            }
                        };
                        }.start();
                }
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= userAppInfos.size()+1){
                    mAppNumTV.setText("系统程序"+systemAppInfos.size()+1);
                }else{
                    mAppNumTV.setText("用户程序"+userAppInfos.size()+1);
                }

            }
        });
    }


}
