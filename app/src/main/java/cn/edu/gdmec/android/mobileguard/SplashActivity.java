package cn.edu.gdmec.android.mobileguard;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if(1==1){
            System.out.print("asd");
        }

        for(int i=2;i<=3;i++){
            System.out.print("asd");
        }
}
}
