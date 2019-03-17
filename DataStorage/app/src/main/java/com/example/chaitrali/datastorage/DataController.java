package com.example.chaitrali.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DataController {

    private static final String Message = "Message";
    private static final String dbName ="DataStorageAssignment";
    private static final String tableName = "BlogMessage_Table";
    private static final String createtable = "create table BlogMessage_Table (Message text not null);";

    DatabaseHelper dbHelper;
    Context context;
    SQLiteDatabase db;

    public DataController(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }


    public DataController open()
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }


    public void close()
    {
        dbHelper.close();
    }


    public long addData(String message)
    {
        ContentValues content = new ContentValues();
        content.put("Message", message);
        return db.insertOrThrow(tableName, null, content);
    }


    private class DatabaseHelper extends SQLiteOpenHelper{


        public DatabaseHelper(@Nullable Context context) {
            super(context, dbName, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(createtable);
            }catch(SQLiteException ex)
            {
                ex.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS DataStorageAssignment");
            onCreate(db);
        }
    }
}
