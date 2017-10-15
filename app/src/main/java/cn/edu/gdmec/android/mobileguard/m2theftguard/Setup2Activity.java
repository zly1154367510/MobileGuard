package cn.edu.gdmec.android.mobileguard.m2theftguard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RadioButton;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m2theftguard.utils.BaseSetUpActivity;

/**
 * Created by zly11 on 2017/10/13.
 */

public class Setup2Activity  extends BaseSetUpActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_2);
        ((RadioButton)findViewById(R.id.rb_second)).setChecked(true);
    }

    @Override
    public void showNext() {

        startActivityFinishSelf (Setup3Activity.class);
    }

    @Override
    public void showPre() {

        startActivityFinishSelf (Setup1Activity.class);
    }
}
