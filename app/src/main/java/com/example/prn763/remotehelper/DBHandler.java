package com.example.prn763.remotehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PRN763 on 12/29/2017.
 */

public class DBHandler extends SQLiteOpenHelper {
    public static DBHandler mDbHandler;

    private static final String DATABASE_NAME = "ContactDB";
    private static final String TABLE_NAME = "Contact";
    private static final String COL_1_ID = "ID";
    private static final String COL_2_USER_NAME = "NAME";
    private static final String COL_3_PHONE_NUMBER = "NUMBER";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static DBHandler getInstance(Context context){
        if(mDbHandler == null){
            mDbHandler = new DBHandler(context);
        }
        return mDbHandler;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_QUERY = "create table " + TABLE_NAME + " (" + COL_1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2_USER_NAME +" TEXT,"+COL_3_PHONE_NUMBER+" TEXT)";
        sqLiteDatabase.execSQL(SQL_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String SQL_QUERY = "DROP TABLE IF EXISTS"+TABLE_NAME;
        sqLiteDatabase.execSQL(SQL_QUERY);
        onCreate(sqLiteDatabase);
    }

    public boolean insertDatabase(String name, String number){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_USER_NAME, name);
        contentValues.put(COL_3_PHONE_NUMBER, number);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if(result >= 0){
            return true;
        }
        else{
            return false;
        }
    }

    public Cursor getDataBase(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String SQL_QUERY = "select * from "+TABLE_NAME;
        Cursor res = sqLiteDatabase.rawQuery(SQL_QUERY, null);
        return res;
    }
}
