package cn.edu.gdmec.android.mobileguard.m2theftguard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RadioButton;

import cn.edu.gdmec.android.mobileguard.m2theftguard.utils.BaseSetUpActivity;

/**
 * Created by zly11 on 2017/10/13.
 */

public class Setup3Activity  extends BaseSetUpActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.edu.gdmec.android.mobileguard.R.layout.activity_setup_3);
        ((RadioButton)findViewById(cn.edu.gdmec.android.mobileguard.R.id.rb_third)).setChecked(true);
    }

    @Override
    public void showNext() {

        startActivityFinishSelf (Setup4Activity.class);
    }

    @Override
    public void showPre() {

        startActivityFinishSelf (Setup2Activity.class);
    }
}
