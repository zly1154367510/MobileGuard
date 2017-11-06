package cn.edu.gdmec.android.mobileguard.m4appmanager.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

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
}
