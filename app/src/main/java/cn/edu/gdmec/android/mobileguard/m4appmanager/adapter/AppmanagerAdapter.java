package cn.edu.gdmec.android.mobileguard.m4appmanager.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.Format;
import java.util.List;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;
import cn.edu.gdmec.android.mobileguard.m4appmanager.utils.DensityUtil;
import cn.edu.gdmec.android.mobileguard.m4appmanager.utils.EngineUtils;

/**
 * Created by zly11 on 2017/11/6.
 */

public class AppmanagerAdapter extends BaseAdapter {
    private List<AppInfo> UserAppInfos;
    private List<AppInfo> SystemAppInfos;
    private Context context;

    public AppmanagerAdapter (List<AppInfo> userAppInfos,List<AppInfo> SystemAppInfos,Context context){
        super();
        this.UserAppInfos = userAppInfos;
        this.SystemAppInfos = SystemAppInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return UserAppInfos.size()+SystemAppInfos.size()+2;
    }

    @Override
    public Object getItem(int position) {
        if (position == 0){
            return null;
        }else if(position == (UserAppInfos.size()+1)){
            return null;
        }
        AppInfo appInfo;
        if (position<(UserAppInfos.size()+1)){
            appInfo = UserAppInfos.get(position-1);
        }else{
            int location = position - UserAppInfos.size() - 2;
            appInfo = SystemAppInfos.get(location);
        }
        return appInfo;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        if (i == 0 ){
            TextView tv = getTextView();
            tv.setText("用户程序"+UserAppInfos.size()+"个");
            return tv;
        }else if (i == UserAppInfos.size() + 1){
            TextView tv = getTextView();
            tv.setText("系统程序" +  SystemAppInfos.size()+"个");
            return tv;
        }

        AppInfo appinfo;
        if (i < (UserAppInfos.size()+1)){
            appinfo = UserAppInfos.get(i - 1);
        }else{
            appinfo = SystemAppInfos.get(i - UserAppInfos.size()-2);
        }
        ViewHolder holder = null;
        if (view != null & view instanceof  LinearLayout){
            holder = (ViewHolder)view.getTag();
        }else{
            holder = new ViewHolder();
            view = View.inflate(context,R.layout.item_appmanager_list,null);
            holder.mAppIncoImgv = (ImageView)view.findViewById(R.id.imgv_appicon);
            holder.mAppLocationTv = (TextView)view.findViewById(R.id.tv_appisroom);
            holder.mAppsizeTV = (TextView)view.findViewById(R.id.tv_appsize);
            holder.mAppNameTV = (TextView)view.findViewById(R.id.tv_appname);
            holder.mLuanchAppTv = (TextView)view.findViewById(R.id.tv_launch_app);
            holder.mSettingAppTV = (TextView)view.findViewById(R.id.tv_setting_app);
            holder.mShareAppTV = (TextView)view.findViewById(R.id.tv_share_app);
            holder.mUnistallTV = (TextView)view.findViewById(R.id.tv_uninstall_app);
            holder.mAppOptionLL = (LinearLayout) view.findViewById(R.id.ll_option_app);
            holder.mAboutTV = (TextView)view.findViewById(R.id.tv_about_app);
            view.setTag(holder);

        }
        if (appinfo != null){
            holder.mAppLocationTv.setText(appinfo.getAppLocation(appinfo.isInRoom));
            holder.mAppIncoImgv.setImageDrawable(appinfo.icon);
            holder.mAppsizeTV.setText(Formatter.formatFileSize(context,appinfo.appSize));
            holder.mAppNameTV.setText(appinfo.appName);
            if (appinfo.isSelected){
                holder.mAppOptionLL.setVisibility(View.VISIBLE);
            }else{
                holder.mAppOptionLL.setVisibility(View.GONE);
            }

        }
        MyClickListener listener = new MyClickListener(appinfo);
        holder.mLuanchAppTv.setOnClickListener(listener);
        holder.mSettingAppTV.setOnClickListener(listener);
        holder.mShareAppTV.setOnClickListener(listener);
        holder.mUnistallTV.setOnClickListener(listener);
        holder.mAboutTV.setOnClickListener(listener);

        return view;
    }





    @Override
    public long getItemId(int position) {
        return 0;
    }



    public TextView getTextView(){
        TextView tv = new TextView(context);
        tv.setBackgroundColor(ContextCompat.getColor(context,R.color.graye5));
        tv.setPadding(DensityUtil.dip2px(context,5),
                DensityUtil.dip2px(context,5),
                DensityUtil.dip2px(context,5),
                DensityUtil.dip2px(context,5));
        tv.setTextColor(ContextCompat.getColor(context,R.color.black));
        return tv;
    }
    static class ViewHolder{
        TextView mAppLocationTv;
        TextView mLuanchAppTv;
        TextView mUnistallTV;
        TextView mShareAppTV;
        TextView mSettingAppTV;
        ImageView mAppIncoImgv;
        TextView mAppsizeTV;
        TextView mAppNameTV;
        TextView mAboutTV;
        LinearLayout mAppOptionLL;
    }
    class MyClickListener implements View.OnClickListener{

        private AppInfo appInfo;

        public MyClickListener(AppInfo appInfo){
            super();
            this.appInfo = appInfo;
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.tv_launch_app:
                    EngineUtils.startApplication(context,appInfo);
                    break;
                case R.id.tv_share_app:
                    EngineUtils.shareApplication(context,appInfo);
                    break;
                case R.id.tv_setting_app:
                    EngineUtils.SettingAppDetail(context,appInfo);
                    break;
                case R.id.tv_uninstall_app:
                    break;
                case R.id.tv_about_app:
                    EngineUtils.aboutApplication(context,appInfo);
                    break;
            }

        }
    }
}
