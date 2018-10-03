package com.example.preet.bloodbank;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by preet on 05/07/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private  static  final int DATABASE_VERSION=6;
    private  static  final String DATABASE_NAME="students.db";
    private  static  final String TABLE_NAME="student_table";
    private  static  final String COL_1="ID";
    private  static  final String COL_2="NAME";
    private  static  final String COL_3="SURNAME";
   private  SQLiteOpenHelper openHelper;
    private  Example ourDbHelper;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
String CREATE_CONTACTS_TABLE="CREATE TABLE "+TABLE_NAME+"("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_2+" TEXT,"+COL_3+" TEXT)";
db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);

    }
    public boolean insertData(String name,String surname)throws SQLException{
        SQLiteDatabase ourDatabase = getWritableDatabase();
    ContentValues contentValues=new ContentValues();
    contentValues.put(COL_2,name);
    contentValues.put(COL_3, surname);
        long result;
        result=ourDatabase.insertOrThrow(TABLE_NAME,null,contentValues);
        if(result==-1)
        {
            return false;}
            else
            {
                return true;
            }
        }
    public Integer deletedata(String id)
    {
        SQLiteDatabase db=getWritableDatabase();
        int result=db.delete(TABLE_NAME,"id= ?", new String[] {id});
        return result;
    }
    
    public boolean updateData(String id, String name,String surname)throws SQLException{
        SQLiteDatabase ourDatabase = getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3, surname);
        ourDatabase.update(TABLE_NAME,contentValues, "id= ?", new String[] {id});
            return true;
        }
    public  Cursor getAllData()throws SQLException
    {
        SQLiteDatabase ourDatabase = getWritableDatabase();
        Cursor res=ourDatabase.rawQuery("select * from " +TABLE_NAME,null);
        return  res;
    }
    }
    




