package com.example.spotify_wrapper20.ui.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VER = 1;
    private static final String DB_NAME = "AccountInformation";
    public DatabaseHelper(Context context){
        super(context, DB_NAME,null,DB_VER);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlDDL = "CREATE TABLE accountInfo (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, username TEXT, password TEXT)";
        db.execSQL(sqlDDL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        String sqlRecreate = "DROP TABLE IF EXISTS accountInfo";
        database.execSQL(sqlRecreate);
        onCreate(database);
    }
}
