package cn.edu.gdmec.android.mobileguard;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by zly11 on 2017/10/19.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

    }
    //检查SIM卡是否更换方法
    public void correctSIM(){
        SharedPreferences sp=getSharedPreferences("config", Context.MODE_PRIVATE);
        boolean protecting = sp.getBoolean("protecting",true);
        if (protecting){
            String simNum=sp.getString("sim","");
            TelephonyManager mTelephoneManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String smimm=mTelephoneManager.getSimSerialNumber();
            smimm="999";
            if (simNum.equals(smimm)) {
                Log.i("","sim卡未发生改变");
            }else{
                Log.i("","sim卡发生改变");
                //取出选择的安全号码
                String helpPhoneNumber = sp.getString("help","");
                if (!TextUtils.isEmpty(helpPhoneNumber)){
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(helpPhoneNumber,null,"您的好友手机sim卡被更换",null,null);
                }
            }
        }
    }
}
