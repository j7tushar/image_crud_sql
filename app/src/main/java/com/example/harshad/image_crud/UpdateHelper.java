package com.example.harshad.image_crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Harshad on 09/09/2016.
 */
public class UpdateHelper {
    private Context context;
    private SQLiteDatabase mDb;
    private DBHelper dbHelper;

    public UpdateHelper(Context context) {
        this.context = context;
    }

    public UpdateHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        mDb = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long update_profile(String id, byte[] byteImage, String data) {

        ContentValues Values = new ContentValues();
        Values.put(DBHelper.IMAGE_NAME,data);
        Values.put(DBHelper.IMAGE_,byteImage);
        return mDb.update(DBHelper.IMAGE_TABLE, Values, DBHelper.IMAGE_ID + "=" + id, null);

    }
}
