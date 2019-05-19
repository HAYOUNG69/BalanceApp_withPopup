package com.example.programmingknowledge.mybalance_v11;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//혜린's DBHelper ^_^
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public DBHelper(Context context){
        super(context, "goalbalancedb", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String goalbalanceSQL = "CREATE TABLE tb_goalbalance "+
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "sleep REAL,"+"work REAL,"+"study REAL,"+"exercise REAL,"+"leisure REAL,"+"other REAL,"+"week TEXT);";
        db.execSQL(goalbalanceSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        if(newVersion == DATABASE_VERSION){
            db.execSQL("drop table tb_goalbalance");
            onCreate(db);
        }
    }
}
