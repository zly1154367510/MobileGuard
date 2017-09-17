package cn.edu.gdmec.android.mobileguard.m1home.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by zly11 on 2017/9/12.
 */

public class MyUtils {
    /*
    静态方法 获取当前版本号
     */
    public static String getVersion(Context context){
        PackageManager packageManager=context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return " ";
        }
    }
}
