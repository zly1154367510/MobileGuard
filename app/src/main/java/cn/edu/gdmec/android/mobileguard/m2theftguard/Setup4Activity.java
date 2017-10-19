package cn.edu.gdmec.android.mobileguard.m2theftguard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by zly11 on 2017/10/13.
 */

public class Setup4Activity  extends BaseSetUpActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(cn.edu.gdmec.android.mobileguard.R.layout.activity_setup_4);
        ((RadioButton)findViewById(cn.edu.gdmec.android.mobileguard.R.id.rb_four)).setChecked(true);
    }

    @Override
    public void showNext() {
        Toast.makeText(this, "过不去了", Toast.LENGTH_LONG).show();


    }

    @Override
    public void showPre() {

        startActivityFinishSelf (Setup3Activity.class);
    }
}
