package cn.edu.gdmec.android.mobileguard.m5virusscan.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zly11 on 2017/11/13.
 */



public class AntiVirusDao {
    //检查某个md5是否是病毒

    private static Context context;
    private static String dbname;
    public AntiVirusDao(Context context){
        this.context = context;
        dbname = "/data/data/"+context.getPackageName ()+"/files/antivirus.db";
    }
    //使用apk文件的md5值匹配病毒数据库
    public String checkVirus(String md5){
        String desc = null;
        //打开病毒数据库
        SQLiteDatabase db = SQLiteDatabase.openDatabase (
                dbname, null,
                SQLiteDatabase.OPEN_READONLY );
        Cursor cursor = db.rawQuery ( "select desc from datable where md5=?",
                new String[] { md5 });
        if (cursor.moveToNext ()){
            desc = cursor.getString ( 0 );
        }
        cursor.close ();
        db.close ();
        return desc;
    }

    public String getVirusDbVersion(){
        String dbVersion = null;
        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                dbname, null,
                SQLiteDatabase.OPEN_READONLY);
        //Cursor cursor = db.rawQuery("select major||'.'||minor||'.'||build from version",null);

//        if (cursor.moveToNext()) {
//            dbVersion = cursor.getString(0);
//        }
//        cursor.close();
//        db.close();
        return dbVersion;
    }
}