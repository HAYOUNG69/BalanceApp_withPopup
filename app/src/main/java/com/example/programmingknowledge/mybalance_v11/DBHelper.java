package com.example.programmingknowledge.mybalance_v11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//혜린's DBHelper ^_^
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public DBHelper(Context context){
        super(context, "balanceappdb.db", null, DATABASE_VERSION);
    }
    private static final String CREATE_TABLE_GOALBALANCE = "CREATE TABLE tb_goalbalance "+
            "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "sleep REAL,"+"work REAL,"+"study REAL,"+"exercise REAL,"+"leisure REAL,"+"other REAL,"+"week TEXT);";
    private static final String CREATE_TABLE_TODAYCOUNT = "CREATE TABLE todaycount "
                    +"(id INTEGER PRIMARY KEY AUTOINCREMENT, "+"place TEXT, "+"category TEXT,"
                    +"starttime DATETIME,"+"endtime DATETIME, "+"week TEXT);";



    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_GOALBALANCE);
        db.execSQL(CREATE_TABLE_TODAYCOUNT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if(newVersion == DATABASE_VERSION){
            db.execSQL("DROP TABLE IF EXISTS tb_goalbalance");
            db.execSQL("DROP TABLE IF EXISTS todaycount");


            onCreate(db);
        }
    }
}
