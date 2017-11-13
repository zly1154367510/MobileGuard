package cn.edu.gdmec.android.mobileguard.m5virusscan.adapter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m5virusscan.entity.ScanAppInfo;

/**
 * Created by zly11 on 2017/11/13.
 */

public class ScanVirusAdapter extends BaseAdapter {
    private List<ScanAppInfo> mScanAppInfo;
    private Context context;
    public ScanVirusAdapter(Context context,List<ScanAppInfo> ScanAppInfos){
        super();
        this.context = context;
        mScanAppInfo = ScanAppInfos;
    }

    static class ViewHolder{
        ImageView mAppIconImgv;
        TextView mAppNameTV;
        ImageView mScaIconImgv;
    }

    @Override
    public int getCount() {
        return mScanAppInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mScanAppInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = convertView.inflate(context, R.layout.item_list_applock,null);
            holder = new ViewHolder();
            holder.mAppIconImgv = (ImageView) convertView.findViewById(R.id.imgv_appicon);
            holder.mAppNameTV = (TextView)convertView.findViewById(R.id.tv_appname);
            holder.mScaIconImgv = (ImageView)convertView.findViewById(R.id.imgv_lock);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        ScanAppInfo scanAppInfo = mScanAppInfo.get(position);
        if (!scanAppInfo.isVirus){
            holder.mScaIconImgv.setBackgroundResource(R.drawable.blue_right_icon);
            holder.mAppNameTV.setTextColor(context.getResources().getColor(R.color.black));
            holder.mAppNameTV.setText(scanAppInfo.appName);
        }else{
            holder.mAppNameTV.setTextColor(context.getResources().getColor(R.color.bright_red));
            holder.mAppNameTV.setText(scanAppInfo.appName+"("+scanAppInfo.description+")");
        }
        holder.mAppIconImgv.setImageDrawable(scanAppInfo.appicon);
        return convertView;
    }
}
