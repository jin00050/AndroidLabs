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

    public static String DB_Name = "Messages1.db1";
    public static int DB_version =2;
    static final String DB_table = "Message_DB1";
    private ChatDatabaseHelper dbHelper;
    static final String key_RowID = "_id";
    static final String key_message = "MESSAGE";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, "Messages.db1", null,DB_version);
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
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DB_table); //delete what was there previously
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, newVersion=" + newVersion + "oldVersion=" + oldVersion);
    }
    @Override
    public void onOpen(SQLiteDatabase db)
    {
        Log.i("Database ", "onOpen was called");
    }

    public void deleteItem(String id){
        getWritableDatabase().execSQL("DELETE FROM " + DB_table + " WHERE " + key_RowID + " = " + id);
    }
}
