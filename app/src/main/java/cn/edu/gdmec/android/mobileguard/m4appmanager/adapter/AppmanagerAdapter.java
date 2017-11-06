package cn.edu.gdmec.android.mobileguard.m4appmanager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;

/**
 * Created by zly11 on 2017/11/6.
 */

public class AppmanagerAdapter extends BaseAdapter {
    private List<AppInfo> UserAppInfo;
    private List<AppInfo> SystemAppInfo;
    private Context context;
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
