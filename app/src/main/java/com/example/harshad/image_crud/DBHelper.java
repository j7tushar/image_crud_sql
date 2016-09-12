package com.example.harshad.image_crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Harshad on 09/09/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    static DBHelper dbhelper;
    static final String DATABASE_NAME = "IMAGE_EX";
    static final int DATABASE_VERSION = 1;
    public static final String IMAGE_TABLE="image_table";
    public static final String IMAGE_="image";
    public static final String IMAGE_NAME="image_name";
    public static final String IMAGE_ID="id";

    public static final String IMAGE_EX = "CREATE TABLE "+IMAGE_TABLE+" ("+IMAGE_ID + " INTEGER PRIMARY KEY,"+IMAGE_NAME+ " VARCHAR(55) DEFAULT NULL," + IMAGE_+" BLOB DEFAULT NULL);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(IMAGE_EX);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion+ ". Old data will be destroyed");
        db.execSQL("DROP TABLE IF EXISTS"+ IMAGE_TABLE);
    }

}