package cn.edu.gdmec.android.mobileguard.m1home;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m1home.adapter.HomeAdapter;
import cn.edu.gdmec.android.mobileguard.m2theftguard.dialog.InterPasswordDialog;
import cn.edu.gdmec.android.mobileguard.m2theftguard.dialog.SetUpPasswrodDialog;
import cn.edu.gdmec.android.mobileguard.m2theftguard.LostFindActivity;
import cn.edu.gdmec.android.mobileguard.m2theftguard.receiver.MyDeviceAdminReceiver;
import cn.edu.gdmec.android.mobileguard.m2theftguard.utils.MD5Utils;
import cn.edu.gdmec.android.mobileguard.m3communicationguard.SecurityPhoneActivity;
import cn.edu.gdmec.android.mobileguard.m4appmanager.AppManagerActivity;
import cn.edu.gdmec.android.mobileguard.m5virusscan.VirusScanActivity;

public class HomeActivity extends AppCompatActivity {
    private GridView gv_home;
    private long mExiTime;
    private SharedPreferences msharedPreferences;
    private DevicePolicyManager policyManager;
    private ComponentName componentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //getSupportActionBar().hide();
        msharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        gv_home=(GridView)findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if (isSetUpPassword()){
                            setInterPswdDialog();

                        }else{
                            showSetUpPswdDialog();
                        }
                        break;
                    case 1:
                        startActivity(SecurityPhoneActivity.class);
                        break;
                    case 2:
                        startActivity(AppManagerActivity.class);
                        break;
                    case 3:
                        startActivity(VirusScanActivity.class);
                        break;
                }
            }
        });
        policyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, MyDeviceAdminReceiver.class);
        boolean active = policyManager.isAdminActive(componentName);
        if (!active){
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"获取超级管理员权限，用于元辰锁屏和清除数据");
            startActivity(intent);

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if((System.currentTimeMillis()-mExiTime)<2000){
                System.exit(0);
            }else{
                Toast.makeText(this,"请在按一次退出程序",Toast.LENGTH_LONG).show();
                mExiTime=System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /*
    托管设置密码弹出框方法
    。判断输入密码
    。
     */
    public void showSetUpPswdDialog(){
        final SetUpPasswrodDialog setUpPasswrodDialog = new SetUpPasswrodDialog(HomeActivity.this);

        setUpPasswrodDialog.setMyCallBack(new SetUpPasswrodDialog.MyCallBack() {
            @Override
            public void ok() {
                String firstPwsd = setUpPasswrodDialog.mFirstPWDET.getText().toString().trim();
                String affirmPwsd = setUpPasswrodDialog.mAffirmET.getText().toString().trim();
                if(!TextUtils.isEmpty(firstPwsd)&&!TextUtils.isEmpty(affirmPwsd)){//两个密码输入框不为空
                    if (firstPwsd.equals(affirmPwsd)){//如果两个密码框输入一样
                         savePWD(firstPwsd);
                         setUpPasswrodDialog.dismiss();
                        Toast.makeText(HomeActivity.this,"设置成功",Toast.LENGTH_LONG).show();
                        setInterPswdDialog();

                    }else{
                        Toast.makeText(HomeActivity.this,"两次密码输入不同",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(HomeActivity.this,"设置密码不能为空",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void cancel() {
             setUpPasswrodDialog.dismiss();
            }
        });
        setUpPasswrodDialog.setCancelable(true);
        setUpPasswrodDialog.show();
    }
    /*
    设置托管密码输入框方法
     */
    public void setInterPswdDialog(){

        final String password=getPassword();
        final InterPasswordDialog mInPswdDialog=new InterPasswordDialog(HomeActivity.this);

        mInPswdDialog.setMyCallBack(new InterPasswordDialog.MyCallBack() {
            @Override
            public void ok() {
               if (TextUtils.isEmpty(mInPswdDialog.getPassword())){

                   Toast.makeText(HomeActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
               }else if (password.equals(MD5Utils.encode(mInPswdDialog.getPassword()))){
                   mInPswdDialog.dismiss();
                   startActivity(LostFindActivity.class);
                   Toast.makeText(HomeActivity.this,"可以进入手机防盗",Toast.LENGTH_LONG).show();
               }else{
                   mInPswdDialog.dismiss();
                   Toast.makeText(HomeActivity.this,"密码错误",Toast.LENGTH_LONG).show();
               }
            }

            


            @Override
            public void cancel() {
                mInPswdDialog.dismiss();
            }
        });
        mInPswdDialog.setCancelable(true);
        mInPswdDialog.show();
    }




    /*
    判断用户是否设置手机防盗密码
     */
    public boolean isSetUpPassword(){
        String password = msharedPreferences.getString("PhoneAntiTheftPWD",null);
        if (TextUtils.isEmpty(password)){
            return false;
        }else{
            return true;
        }
    }

    /*
    手机防盗板块
    储存设置的密码方法
     */
    public void savePWD(String password){
        SharedPreferences.Editor editor = msharedPreferences.edit();
        editor.putString("PhoneAntiTheftPWD", MD5Utils.encode(password));
        editor.commit();
    }

    /*
    手机防盗模块

    获取密码方法

    return sharedPreferences存储的密码
     */

    public String getPassword(){
        String password=msharedPreferences.getString("PhoneAntiTheftPWD",null);
        if (TextUtils.isEmpty(password)){
            return "";
        }
        return password;
    }

    //从homeActivity跳转到cls
    //class<？>范类
    public void startActivity(Class<?> cls){
        Intent intent = new Intent(HomeActivity.this,cls);
        startActivity(intent);
    }

}
