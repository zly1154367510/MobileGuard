package cn.edu.gdmec.android.mobileguard.m2theftguard;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;

/**
 * Created by 通哥 on 2017/10/12.
 */

public abstract class BaseSetUpActivity extends AppCompatActivity {
    public SharedPreferences sp;
    private GestureDetector mGestureDetector;
    public abstract void showNext();
    public abstract void showPre();
    //用手势识别器去识别触控事件
}
