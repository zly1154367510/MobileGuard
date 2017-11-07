package cn.edu.gdmec.android.mobileguard.m4appmanager.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by zly11 on 2017/11/6.
 */

public class AppInfo {
    public String packageName;

    public Drawable icon;

    public String appName;

    public String apkPath;

    public long appSize;

    public boolean isInRoom;

    public boolean isUserApp;

    public boolean isSelected = false;

    public String getAppLocation(boolean isInRoom){
        if (isInRoom){
            return "手机存储";
        }else{
            return "外部存储";
        }
    }
}