package cn.edu.gdmec.android.mobileguard.m5virusscan.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m1home.HomeActivity;
import cn.edu.gdmec.android.mobileguard.m1home.entity.VersionEntity;
import cn.edu.gdmec.android.mobileguard.m5virusscan.entity.ScanVersionEntity;

/**
 * Created by zly11 on 2017/11/14.
 */

public class VersionUpdate {

    private String mVersion;
    private Activity context;
    private ScanVersionEntity versionEntity;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

                case 100:
                    showUpdateDialog(versionEntity);
                    break;

            }

        }
    };

    public void getCloudVersion(){
        try {
            HttpClient httpClient=new DefaultHttpClient();
            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),5000);
            HttpConnectionParams.setSoTimeout(httpClient.getParams(),5000);
            HttpGet httpGet=new HttpGet("http://android2017.duapp.com/virusupdateinfo.html");
            HttpResponse execute = httpClient.execute(httpGet);
//            if (execute.getStatusLine().getStatusCode()==200){
//                HttpEntity httpEntity=execute.getEntity();
//                String result= EntityUtils.toString(httpEntity,"utf-8");
//                JSONObject jsonObject=new JSONObject(result);
//                versionEntity=new ScanVersionEntity();
//                versionEntity.versionCode=jsonObject.getString("code");
//                versionEntity.description=jsonObject.getString("des");
//                versionEntity.apkurl=jsonObject.getString("apkurl");
//                if(!"200".equals(versionEntity.versionCode)){
                  /*
                  如果当前版本与服务器不匹配
                  调用Handler的sendEmptyMessage()方法发送常量给handler的实例
                  通过实例的handleMessage方法对常量判断在处理
                   */
                    handler.sendEmptyMessage(100);
                //}
          //  }

        }catch (IOException e){

            e.printStackTrace();
        }

    }
    public void showUpdateDialog(final ScanVersionEntity versionEntity){
        /*
        对话框对象
         */
        final AlertDialog.Builder buile=new AlertDialog.Builder(context);
        buile.setTitle("发现新版本"+versionEntity.versionCode);
        buile.setMessage(versionEntity.description);
        buile.setCancelable(false);//不允许跳过
        buile.setIcon(R.mipmap.ic_launcher_round);

        /*
        设置升级按钮事件
         */
        buile.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //downLoadNewApk(versionEntity.apkurl);
            }
        });
         /*
        暂不升级按钮事件
         */
        buile.setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              //  enterHome();
                dialog.dismiss();
            }
        });
        buile.show();

    }
}
