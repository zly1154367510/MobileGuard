package cn.edu.gdmec.android.mobileguard.m3communicationguard.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.mobileguard.m3communicationguard.db.BlackNumberOpenHelper;
import cn.edu.gdmec.android.mobileguard.m3communicationguard.entity.BlackContactInfo;

/**
 * Created by zly11 on 2017/10/30.
 */

public class BlackNumberDao {
    private BlackNumberOpenHelper blackNumberOpenHelper;
    public BlackNumberDao(Context context){
        super();
        blackNumberOpenHelper = new BlackNumberOpenHelper(context,"blackNumber.db",null,1);
    }

    //插入数据
    public boolean add(BlackContactInfo blackContactInfo){
        SQLiteDatabase db = blackNumberOpenHelper.getWritableDatabase();
        //ContentValues对象用于储存基本数据类型
        ContentValues values = new ContentValues();
        //规范手机号码格式
        if (blackContactInfo.phoneNumber.startsWith("+86")){
            blackContactInfo.phoneNumber = blackContactInfo.phoneNumber.substring(3,blackContactInfo.phoneNumber.length());
        }
        //向ContentValues加入数据
        values.put("number",blackContactInfo.phoneNumber);
        values.put("name",blackContactInfo.contactName);
        values.put("mode", blackContactInfo.mode);
        long rowid = db.insert("blacknumber",null,values);
        if (rowid == -1){
            return false;
        }else{
            return true;
        }
    }

    //删除数据
    public boolean delete(BlackContactInfo blackContactInfo){
        SQLiteDatabase db = blackNumberOpenHelper.getWritableDatabase();
        int rowNumber = db.delete("blacknumber","number=?",new String[]{blackContactInfo.phoneNumber});
        if (rowNumber == 0){
            return false;
        }else{
            return true;
        }
    }

    //分页查询数据
    public List<BlackContactInfo> getPageBlackNumber(int pagenumber,int pagesize){
        SQLiteDatabase db = blackNumberOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select number.mode,name from blacknumber limit ? offset ?",new String[]{String.valueOf(pagenumber),String.valueOf(pagesize*pagenumber)});
        List<BlackContactInfo> mBlackContactInfos = new ArrayList<BlackContactInfo>();
        while(cursor.moveToNext()){
            BlackContactInfo info = new BlackContactInfo();
            info.phoneNumber = cursor.getString(0);
            info.mode = cursor.getInt(1);
            info.contactName = cursor.getString(2);
            mBlackContactInfos.add(info);
        }
        cursor.close();
        db.close();
        SystemClock.sleep(30);
        return mBlackContactInfos;

    }

    public int getBlackContactMode(String number){
        Log.d("incoming phonenumber",number);
        SQLiteDatabase db = blackNumberOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("blacknumber",new String[]{"mode"},"number=?",new String[]{number},null,null,null);
        int mode = 0;
        if (cursor.moveToNext()){
            mode = cursor.getInt(cursor.getColumnIndex("mode"));
        }
        cursor.close();
        db.close();
        return mode;
    }

    public int getTotalNumber(){
        SQLiteDatabase db = blackNumberOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from blacknumber",null);
        cursor.moveToNext();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }
}
