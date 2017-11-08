package cn.edu.gdmec.android.mobileguard.m4appmanager.DiaLog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by zly11 on 2017/11/7.
 */

public class appMessageDialog extends Dialog{
    private Context context;
    public appMessageDialog (@NonNull Context context){
        super(context, R.style.dialog_custom);
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.inter_password_dialog);
        super.onCreate(savedInstanceState);
        initView();
    }
    public void initView(){

    }
}
