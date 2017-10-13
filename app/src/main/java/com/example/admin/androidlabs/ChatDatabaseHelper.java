package com.example.admin.androidlabs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * Created by Admin on 2017-10-09.
 */
public  class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static String DB_Name = "Messages.db";
    public static int DB_version =2;
    static final String DB_table = "Message_DB";
    private ChatDatabaseHelper dbHelper;
    SQLiteDatabase db;

    static final String key_RowID = "_id";
    static final String key_message = "MESSAGE";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, "Messages.db", null,DB_version);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + DB_table +"( _id INTEGER PRIMARY KEY AUTOINCREMENT, MESSAGE text);");
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }
    public void onUpgrade(SQLiteDatabase db,int newVersion, int oldVersion ){
       //  super(Context ctx ,String "Messages.db" , SQLiteDatabase.CursorFactory factory,int version);
        db.execSQL("DROP TABLE IF EXISTS "+ DB_table);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion="+ oldVersion  + "newVersion=" + newVersion);
     }
    @Override
    public void onOpen(SQLiteDatabase db)
    {
        Log.i("Database ", "onOpen was called");
    }

}
