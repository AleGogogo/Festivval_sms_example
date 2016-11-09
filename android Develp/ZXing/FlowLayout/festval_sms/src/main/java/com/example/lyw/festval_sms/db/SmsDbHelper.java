package com.example.lyw.festval_sms.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lyw.festval_sms.bean.SendMsg;

/**
 * Created by LYW on 2016/11/7.
 */

public class SmsDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "sms_db";
    private static final int DB_VERSSION = 1;
    private static SmsDbHelper smsInstance;

    public static SmsDbHelper getInstance(Context context) {
        if (smsInstance == null) {
            synchronized (SmsDbHelper.class) {
                if (smsInstance == null) {
                    smsInstance = new SmsDbHelper(context);
                }
            }
        }
        return smsInstance;
    }

    private SmsDbHelper(Context context) {
        //getApplicationContext()用这个避免造成内存泄漏的问题;
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + SendMsg.TABLE_NAME + "( _id integer" +
                " primary key autoincrement , "
                + SendMsg.COLUNMS_DATE + " integer , "
                + SendMsg.COLUNMS_FESTVIAL+" text ,"
                +SendMsg.COLUNMS_NAME +" text , "
                +SendMsg.COLUNMS_NUMBERS + " text )";


        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
