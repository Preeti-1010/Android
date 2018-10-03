package com.example.preet.bloodbank;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by preet on 07/07/2016.
 */
public class DonarsDb  extends SQLiteOpenHelper {
    private  static  final int DATABASE_VERSION=1;
    private  static  final String DATABASE_NAME="Client.db";
    private  static  final String TABLE_NAME="Client_table";
    private  static  final String COL_1="EMP_ID";
    private  static  final String COL_2="NAME";
    private  static  final String COL_3="EMAIL";
    private  static  final String COL_4="BLOOD_GROUP";
    private  static  final String COL_5="GENDER";
    private  static  final String COL_6="AGE";
    private  static  final String COL_7="CITY";
    private  static  final String COL_8="MOBILE";
    private  static  final String COL_9="PASSWORD";
    private  static  final String COL_10="CONFIRMPASSWORD";
    private  SQLiteOpenHelper openHelper;
    private  DonarsList ourDbHelper;

    public DonarsDb(Context context) {
        super(context,  DATABASE_NAME,null,DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE="CREATE TABLE "+TABLE_NAME+"("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_2+" TEXT,"+COL_3+" TEXT,"
                +COL_4+" TEXT,"+COL_5+" TEXT,"+COL_6+" INTEGER,"+COL_7+" TEXT, "+COL_8+  "INTEGER,"+COL_9+" TEXT,"+COL_10+" TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);
    }
    public Cursor getAllData()throws SQLException
    {
        SQLiteDatabase ourDatabase = getWritableDatabase();
        Cursor res=ourDatabase.rawQuery("select * from " +TABLE_NAME,null);
        return  res;
    }
}