package cn.edu.gdmec.android.mobileguard.m4appmanager.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;

/**
 * Created by zly11 on 2017/11/6.
 */

public class EngineUtils {

    public static void shareApplication(Context context, AppInfo appInfo){
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"推荐您使用一款软件");
        context.startActivity(intent);
    }

    public static void startApplication(Context context,AppInfo appInfo){
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(appInfo.packageName);
        if (intent != null){
            context.startActivity(intent);
        }else{
            Toast.makeText(context,"无法启动",Toast.LENGTH_SHORT).show();
        }
    }

    public static void SettingAppDetail(Context context,AppInfo appInfo){
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:"+appInfo.packageName));
        context.startActivity(intent);
    }

    public static void unistallApplication(Context context,AppInfo appInfo){
        if (appInfo.isUserApp){
            Intent intent = new Intent();
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:"+appInfo.packageName));
            context.startActivity(intent);
        }else{
            Toast.makeText(context,"系统应用无法删除",Toast.LENGTH_LONG).show();
        }
    }
    public static void aboutApplication(Context context,AppInfo appInfo){
        String verison = "";
        String power[] = null ;
        Signature autoGraph[] ;
        String powers ="" ;
        String s = new String();
        PackageManager pm = context.getPackageManager();
        PackageInfo info = null;
        try {
          info = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_PERMISSIONS);


        }catch (Exception e){
            e.printStackTrace();
        }
        verison = info.versionName;
        power = info.requestedPermissions;

        for (String i : power){
            powers = powers +i+"\n";
        }
        info = null;
        try {
            info = pm.getPackageInfo(appInfo.packageName, PackageManager.GET_SIGNATURES);
            byte[] ss = info.signatures[0].toByteArray();
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(ss));
            if (cert!=null){
                s = cert.getIssuerDN().toString();
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("关于应用").setMessage("Version:"+verison+"\nInstall issuer:"+s+"\nPermissions:"+powers).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


}
