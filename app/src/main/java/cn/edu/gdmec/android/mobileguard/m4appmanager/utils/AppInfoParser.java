package cn.edu.gdmec.android.mobileguard.m4appmanager.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;

/**
 * Created by zly11 on 2017/11/6.
 */

public class AppInfoParser {
    /*
    用于获取手机所有应用程序
     */

    public static List<AppInfo> getAppInfos(Context context){
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        //遍历packgeInfos数组
        for (PackageInfo packageInfo:packageInfos){
            AppInfo appInfo = new AppInfo();
            String packname = packageInfo.packageName;
            appInfo.packageName = packname;
            Drawable icon = packageInfo.applicationInfo.loadIcon(pm);
            appInfo.icon = icon;
            String appname = packageInfo.applicationInfo.loadLabel(pm).toString();
            appInfo.appName = appname;
            String apkPath = packageInfo.applicationInfo.sourceDir;
            appInfo.apkPath = apkPath;
            //用获取的apk路径打开文件流
            File file = new File(apkPath);
            long appSize = file.length();
            appInfo.appSize = appSize;
            int flags = packageInfo.applicationInfo.flags;
            //判断app安装位置
            if ((ApplicationInfo.FLAG_EXTERNAL_STORAGE&flags) != 0){
                appInfo.isInRoom = false;
            }else{
                appInfo.isInRoom = true;
            }
            if ((ApplicationInfo.FLAG_SYSTEM&flags)!=0){
                appInfo.isUserApp = false;
            }else{
                appInfo.isUserApp = true;
            }

            appInfos.add(appInfo);
            appInfo = null;
        }
        return appInfos;
    }
}
