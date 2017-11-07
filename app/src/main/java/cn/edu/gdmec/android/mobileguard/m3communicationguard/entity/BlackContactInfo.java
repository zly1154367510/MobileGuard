package cn.edu.gdmec.android.mobileguard.m3communicationguard.entity;

/**
 * Created by zly11 on 2017/10/30.
 */

public class BlackContactInfo {

    public String phoneNumber;
    public String contactName;
    public int mode;
    public String type;
    public String getContactName(int mode){
        switch (mode){
            case 1:
                return "电话拦截";
            case 2:
                return  "短信拦截";
            case 3:
                return  "电话短信拦截";
        }
        return "";
    }
}
