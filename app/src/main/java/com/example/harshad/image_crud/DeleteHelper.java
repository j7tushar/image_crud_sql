package com.example.harshad.image_crud;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Harshad on 09/09/2016.
 */
public class DeleteHelper {
    private Context context;
    private SQLiteDatabase mDb;
    private DBHelper dbHelper;

    public DeleteHelper(Context context) {
        this.context = context;
    }

    public DeleteHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        mDb = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long delete_profile(String id) {

        return mDb.delete(DBHelper.IMAGE_TABLE, DBHelper.IMAGE_ID + "=" + id, null);

    }
}
