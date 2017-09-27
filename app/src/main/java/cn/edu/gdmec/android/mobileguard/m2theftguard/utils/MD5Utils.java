package cn.edu.gdmec.android.mobileguard.m2theftguard.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 通哥 on 2017/9/26.
 */

public class MD5Utils {

    public static String encode(String text) {

        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : result){
                int number = b&0xff;
                String hex = Integer.toHexString(number);
                if (hex.length()==1){
                    stringBuilder.append("0"+hex);
                }else{
                    stringBuilder.append(hex);
                }
            }
             return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return "";
    }
}
