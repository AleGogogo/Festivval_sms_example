package com.example.lyw.festval_sms.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.lyw.festval_sms.bean.SendMsg;

/**
 * Created by LYW on 2016/11/7.
 */

public class SmsProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.lyw.sms.provider";
    //对外查询通过这个常量获取
    public static final Uri URI_SMS_ALL = Uri.parse("content://" + AUTHORITY
            + "/sms");

    public static UriMatcher mUriMater;

    private static final int CODE_URI_SMS_ALL = 0;
    private static final int CODE_URI_SMS_ONE = 1;
    private SmsDbHelper smsDbHelper;
    private SQLiteDatabase db ;

    static {
        mUriMater = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMater.addURI(AUTHORITY, "sms", CODE_URI_SMS_ONE);
        mUriMater.addURI(AUTHORITY, "sms/#", CODE_URI_SMS_ALL);
    }

    @Override
    public boolean onCreate() {
        smsDbHelper = SmsDbHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int match = mUriMater.match(uri);
        switch (match) {
            case CODE_URI_SMS_ALL:
                break;
            case CODE_URI_SMS_ONE:
                long id = ContentUris.parseId(uri);

                selection = "_id = ?";
                selectionArgs = new String[]{String.valueOf(id)};
                break;
        }
            db = smsDbHelper.getReadableDatabase();
            Cursor cr = db.query(SendMsg.TABLE_NAME, projection, selection,
                    selectionArgs, null, null, sortOrder);

           cr.setNotificationUri(getContext().getContentResolver(),URI_SMS_ALL);
        return cr;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //如果匹配就会返回匹配码
        int match = mUriMater.match(uri);
        if (match != CODE_URI_SMS_ALL)
            throw new IllegalArgumentException("Wrong Uri" + uri);
        db = smsDbHelper.getWritableDatabase();
        long rowId = db.insert(SendMsg.TABLE_NAME, null, values);
        if (rowId > 0) {
            //说明插入成功
            notifyDataSetChange();
            return ContentUris.withAppendedId(uri, rowId);
        }
        db.close();
        return null;
    }

    private void notifyDataSetChange() {
        getContext().getContentResolver().notifyChange(URI_SMS_ALL, null);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
