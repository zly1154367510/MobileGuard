package cn.edu.gdmec.android.mobileguard.m2theftguard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m2theftguard.utils.BaseSetUpActivity;

/**
 * Created by zly11 on 2017/10/12.
 */

public class Setup1Activity extends BaseSetUpActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_1);
        ((RadioButton)findViewById(R.id.rb_first)).setChecked(true);
    }

    @Override
    public void showNext() {

        startActivityFinishSelf (Setup2Activity.class);
    }

    @Override
    public void showPre() {

        Toast.makeText(this,"当前已是第一页",Toast.LENGTH_LONG).show();
    }
}
