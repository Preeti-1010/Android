package com.example.preet.bloodbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by preet on 05/07/2016.
 */
public class RegisterDb extends SQLiteOpenHelper{
    private  static  final int DATABASE_VERSION=12;
    private  static  final String DATABASE_NAME="Client.db";
    private  static  final String TABLE_NAME="Client_table";
    private  static  final String COL_1="ID";
    private  static  final String COL_2="NAME";
    private  static  final String COL_3="EMAIL";
    private  static  final String COL_4="BLOOD_GROUP";
    private  static  final String COL_5="GENDER";
    private  static  final String COL_6="AGE";
    private  static  final String COL_7="CITY";
    private  static  final String COL_8="MOBILE";
    private  static  final String COL_9="PWD";
    private  static  final String COL_10="CPWD";
    private  SQLiteOpenHelper openHelper;
    private  Registration ourDbHelper;

    public RegisterDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE="CREATE TABLE "+TABLE_NAME+"("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_2+" TEXT,"+COL_3+" TEXT,"
       +COL_4+" TEXT,"+COL_5+" TEXT,"+COL_6+" INTEGER,"+COL_7+" TEXT, "+COL_8+  " TEXT,"+COL_9+" TEXT,"+COL_10+" TEXT)";
                db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String name,String email,String bloodgroup,String gender,String age, String city,String mobile,String pwd,String cpwd )throws SQLException {
        SQLiteDatabase ourDatabase = getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, bloodgroup);
        contentValues.put(COL_5, gender);
        contentValues.put(COL_6, age);
        contentValues.put(COL_7, city);
        contentValues.put(COL_8, mobile);
        contentValues.put(COL_9, pwd);
        contentValues.put(COL_10, cpwd);

        long result;
        result=ourDatabase.insertOrThrow(TABLE_NAME, null, contentValues);
        if(result==-1)
        {
            return false;}
        else
        {
            return true;
        }
    }

}
