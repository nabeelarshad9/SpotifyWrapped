package com.example.spotify_wrapper20.ui.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class AccountHandler extends DatabaseHelper{

    public AccountHandler(Context context) {
        super(context);
    }

    //CRUD Operations Create, Read, Update and Delete

    public boolean create (Account account) {
        ContentValues values = new ContentValues();
        values.put("Email", account.getEmail());
        values.put("Username", account.getUsername());
        values.put("Password", account.getPassword());

        SQLiteDatabase Dbase = this.getWritableDatabase();
        boolean isInserted = Dbase.insert("accountInfo",null,values) > 0 ;
        Dbase.close();
        return isInserted;
    }
//    public ArrayList<Account> readtds() {
//        ArrayList<Account> tds = new ArrayList<>();
//        String sqlQ = "SELECT * FROM ToDo ORDER BY id ASC";
//        SQLiteDatabase Dbase = this.getWritableDatabase();
//        Cursor cursor = Dbase.rawQuery(sqlQ,null);
//
//        if(cursor.moveToFirst()){
//            do {
//                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
//                String title = cursor.getString(cursor.getColumnIndex("title"));
//                String description = cursor.getString(cursor.getColumnIndex("description"));
//                Account td = new Account(title,description);
//                td.setId(id);
//                tds.add(td);
//            } while (cursor.moveToNext());
//            cursor.close();
//            Dbase.close();
//        }
//        return tds;
//    }

    public Boolean validateLoginCredentials(String inUsername, String inPassword ){
        //Log.d("LOGIN", "username: " + inUsername);
        Boolean  validCredential = false;
        String sqlQ = "SELECT * FROM accountInfo WHERE username = ? and password = ?";
        SQLiteDatabase Dbase = this.getReadableDatabase();
        Cursor cursor = Dbase.rawQuery(sqlQ,new String[]{inUsername,inPassword});
        if(cursor.moveToFirst()){
            do {
                validCredential = true;
            } while (cursor.moveToNext());
            cursor.close();
            Dbase.close();
        }
        return validCredential;
    }
    public Account readAccountInfo(String inUsername, String inPassword){
        Account account = null;
        String sqlQ = "SELECT * FROM accountInfo WHERE username = ? and password = ?";
        SQLiteDatabase Dbase = this.getWritableDatabase();
        Cursor cursor = Dbase.rawQuery(sqlQ,new String[]{inUsername,inPassword});
        if(cursor.moveToFirst()){
            do {
                int accountId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String password = ""; // Hides password.
                account = new Account(email,username, password);
                account.setId(accountId);
            } while (cursor.moveToNext());
            cursor.close();
            Dbase.close();
        }
        return account;
    }
    public boolean upd(Account accountInfo, int id){
        ContentValues val = new ContentValues();
//        val.put("id",accountInfo.getId());
        val.put("email",accountInfo.getEmail());
        val.put("username",accountInfo.getUsername());
        val.put("password",accountInfo.getPassword());
        SQLiteDatabase Dbase = this.getWritableDatabase();
        boolean isUpdated = Dbase.update("accountInfo",val,"id='"+ id+"'",null) > 0;
//        boolean isUpdated = Dbase.update("accountInfo",val,"email='"+ accountInfo.getEmail()+"'",null) > 0;
        Dbase.close();
        return isUpdated;
    }

    public boolean del(int id) {
        boolean isDeleted;
        SQLiteDatabase Dbase = this.getWritableDatabase();
        isDeleted = Dbase.delete("accountInfo","id='"+id+"'",null) > 0;
        Dbase.close();
        return isDeleted;
    }
}

