package cn.edu.gdmec.android.mobileguard.m8trafficmonitor.db;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 通哥 on 2017/11/30.
 */

public class TrafficOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "traffic.db";
    private static final String TABLE_NAME = "traffic";
    private final static String GPRS = "gprs";
    private final static String TIME = "date";

    public TrafficOpenHelper(Context context){
        super(context, DB_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create table " + TABLE_NAME
                + "(id integer primary key autoincrement," + GPRS
                + " varchar(255)," + TIME +" datetime)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

    }
}