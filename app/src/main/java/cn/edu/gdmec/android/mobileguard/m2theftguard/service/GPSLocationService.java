package cn.edu.gdmec.android.mobileguard.m2theftguard.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.sip.SipAudioCall;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;

/**
 * Created by zly11 on 2017/10/24.
 */

public class GPSLocationService extends Service {
    private LocationManager Lm;
    private MyListener listener;

    @Override
    public void onDestroy() {
        super.onDestroy();
        Lm.removeUpdates(listener);
        listener = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        listener = new MyListener();
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(true);
        String name = Lm.getBestProvider(criteria,true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            return;
        }
        Lm.requestLocationUpdates(name,0,0,listener);

    }
    private class MyListener implements LocationListener{
        @Override
        public void onLocationChanged(Location location) {
            //实例化字符串储存对象
            StringBuilder sb = new StringBuilder();
            //拼接字符串加入储存对象
            sb.append("accuracy:"+location.getAccuracy()+"\n");
            sb.append("speed:"+location.getSpeed()+"\n");
            sb.append("Logitude:"+location.getLongitude()+"\n");
            sb.append("Latitude:"+location.getLatitude()+"\n");
            //转化类型

            //获取安全号码
            SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE);
            String safenumber = sp.getString("safephone","");
            String result = sb.toString();
            //发送短信
            SmsManager.getDefault().sendTextMessage(safenumber,null,result,null,null);
            stopSelf();

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
