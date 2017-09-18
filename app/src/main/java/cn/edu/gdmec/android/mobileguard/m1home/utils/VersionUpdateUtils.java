package cn.edu.gdmec.android.mobileguard.m1home.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.AlteredCharSequence;
import android.widget.Toast;

import org.apache.http.HttpConnection;
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

/**
 * Created by zly11 on 2017/9/12.
 */

public class VersionUpdateUtils {
    /*
    工具类：
      连接服务器
      比较服务器版本
     */
    private String mVersion;
    private Activity context;
    private VersionEntity versionEntity;

    private static final int MESSAGE_IO_ERROR = 102;//常量 io异常信息
    private static final int MESSAGE_JSON_ERROR = 103;//常量 JSON解析异常
    private static final int MESSAGE_SHOW_DIALOG = 104;//输出信息
    private static final int MESSAGE_ENTERHOME = 105;//跳转到主页面

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_JSON_ERROR:
                    Toast.makeText(context,"json错误",Toast.LENGTH_LONG).show();
                    break;
                case MESSAGE_IO_ERROR:
                    Toast.makeText(context,"I/O错误",Toast.LENGTH_LONG).show();
                    break;
                //有新版本更新
                case MESSAGE_SHOW_DIALOG:
                     showUpdateDialog(versionEntity);
                    break;
                case MESSAGE_ENTERHOME:
                    Intent intent=new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                    context.finish();
                    break;
            }

        }
    };


    public VersionUpdateUtils(String mVersion,Activity context){
        this.mVersion=mVersion;
        this.context=context;

    }

    /*
    方法 获取服务器的版本 比较 更新
     */

    public void getCloudVersion(){
      try {
          HttpClient httpClient=new DefaultHttpClient();
          HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),5000);
          HttpConnectionParams.setSoTimeout(httpClient.getParams(),5000);
          HttpGet httpGet=new HttpGet("http://android2017.duapp.com/updateinfo.html");
          HttpResponse execute = httpClient.execute(httpGet);
          if (execute.getStatusLine().getStatusCode()==200){
              HttpEntity httpEntity=execute.getEntity();
              String result= EntityUtils.toString(httpEntity,"utf-8");
              JSONObject jsonObject=new JSONObject(result);
              versionEntity=new VersionEntity();
              versionEntity.versionCode=jsonObject.getString("code");
              versionEntity.description=jsonObject.getString("des");
              versionEntity.apkurl=jsonObject.getString("apkurl");
              if(!mVersion.equals(versionEntity.versionCode)){
                  /*
                  如果当前版本与服务器不匹配
                  调用Handler的sendEmptyMessage()方法发送常量给handler的实例
                  通过实例的handleMessage方法对常量判断在处理
                   */
                  handler.sendEmptyMessage(MESSAGE_SHOW_DIALOG);


              }
          }
          else{
              enterHome();
          }
      }catch (IOException e){
          handler.sendEmptyMessage(MESSAGE_IO_ERROR);
          e.printStackTrace();
      }catch (JSONException e){
          handler.sendEmptyMessage(MESSAGE_JSON_ERROR);
          e.printStackTrace();
      }

    }
    //版本不对应时调用的方法
    public void showUpdateDialog(final VersionEntity versionEntity){
        /*
        对话框对象
         */
        AlertDialog.Builder buile=new AlertDialog.Builder(context);
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
                downLoadNewApk(versionEntity.apkurl);
            }
        });
         /*
        暂不升级按钮事件
         */
         buile.setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 enterHome();
             }
         });
        buile.show();

    }
    //跳转到主页面;
    public void enterHome(){
     handler.sendEmptyMessage(MESSAGE_ENTERHOME);
    }
    public void downLoadNewApk(String apkUrl){
        DownloadUtils downloadUtils=new DownloadUtils();
        downloadUtils.downloadApk(apkUrl,"moblieguard.apk",context);//下载方法
    }


}
