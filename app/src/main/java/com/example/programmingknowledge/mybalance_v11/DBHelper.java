package com.example.programmingknowledge.mybalance_v11;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.w3c.dom.Text;

import java.util.Date;

//혜린's DBHelper ^_^
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

//    public static final String COL_1 = "ID";
//    public static final String COL_2 = "date";
//    public static final String COL_3 = "sleep";
//    public static final String COL_4 = "work";
//    public static final String COL_5 = "study";
//    public static final String COL_6 = "exercise";
//    public static final String COL_7 = "leisure";
//    public static final String COL_8 = "other";
//    public static final String COL_9 = "recommend";



    public DBHelper(Context context){
        super(context, "goalbalancedb", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //목표밸런스
        String goalbalanceSQL = "CREATE TABLE tb_goalbalance "+
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "sleep REAL,"+"work REAL,"+"study REAL,"+"exercise REAL,"+"leisure REAL,"+"other REAL,"+"week TEXT);";
        db.execSQL(goalbalanceSQL);

        //일별 테이블
        String daySQL = "CREATE TABLE tb_dailybalance "+
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "date TEXT,"+ "week TEXT,"+ "sleep REAL,"+"work REAL,"+"study REAL,"+"exercise REAL,"+"leisure REAL,"+"other REAL,"+"recommend TEXT);";
        db.execSQL(daySQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if(newVersion == DATABASE_VERSION){
            db.execSQL("drop table if exists tb_goalbalance");

            db.execSQL("drop table if exists tb_dailybalance");

            onCreate(db);
        }
    }

//    public void insertData(String date, int sleep, int study, int exercise, int leisure, int other, String recommend ){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        ContentValues.put(COL_2, date);
//        ContentValues.put(COL_3,sleep);
//        long result = db.insert(tb_dailybalance, null,contentValues);
//
//    }


}
