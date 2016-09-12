package com.example.harshad.image_crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Harshad on 09/09/2016.
 */
public class InsertHelper
{
    private Context context;
    private SQLiteDatabase mDb;
    private DBHelper dbHelper;

    public InsertHelper(Context context) {
        this.context = context;
    }

    public InsertHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        mDb = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insert_profile(byte[] byteImage, String data){

        ContentValues values = new ContentValues();
        values.put(DBHelper.IMAGE_, byteImage);
        values.put(DBHelper.IMAGE_NAME, data);

        Log.w("Position: ", "Inserted Values-->" + values);

        return mDb.insert(DBHelper.IMAGE_TABLE, null, values);

    }
}
