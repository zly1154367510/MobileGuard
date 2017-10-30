package cn.edu.gdmec.android.mobileguard.m3communicationguard.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zly11 on 2017/10/30.
 */

public class BlackNumberOpenHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "my_info";
    private static int VERSION = 1;
    private static BlackNumberOpenHelper instance = null;
    //数据库连接
    public static BlackNumberOpenHelper getInstance(Context context){
        if (instance==null){
            instance = new BlackNumberOpenHelper(context,DB_NAME,null,VERSION);
        }
        return instance;
    }
    //构造器
    public BlackNumberOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        this.DB_NAME = name;
        this.VERSION = version;
    }
    //创建数据库时调用的方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        //执行sql语句
        db.execSQL("create table blacknumber"+"(id integer primary key autoincrement,"+"number varchar(255),"+"mode integer)");


    }
    //传入版本号不同时调用方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
