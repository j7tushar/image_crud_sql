package com.example.harshad.image_crud;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn_add;
    ListView listView;
    ArrayList<String> image_name = new ArrayList<>();
    ArrayList<byte[]> image = new ArrayList<>();
    ArrayList<String> image_id = new ArrayList<>();
    ProfileListAdapter adapter;
    Intent intent;
    DBHelper dbHelper;
    DeleteHelper del;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = (Button) findViewById(R.id.add);
        listView = (ListView) findViewById(R.id.list);

        // Clear the ArrayLists
        image_name.clear();
        image.clear();
        image_id.clear();

        display_data();

        // the helper class for DB creation operation
        dbHelper = new DBHelper(this);

        // the helper class for doing delete operation
        del = new DeleteHelper(this);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, AddUpdateActivity.class);
                intent.putExtra("update", false);
                startActivity(intent);
                finish();
            }
        });


        // click event for updating the selected profile
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MainActivity.this, AddUpdateActivity.class);
                intent.putExtra("id", image_id.get(position));
                intent.putExtra("image", image.get(position));
                intent.putExtra("name", image_name.get(position));
                intent.putExtra("update", true);
                startActivity(intent);
            }
        });// long click event for deleting the selected profile
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                del.open();
                long ret = del.delete_profile(image_id.get(position));
                del.close();

                if (ret > 0) {
                    Toast.makeText(getApplicationContext(), "Successfully Deleted!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Successfully Deleted!", Toast.LENGTH_SHORT).show();

                    // default function to call the same class
                    recreate();
                }

                return true;
            }
        });

    }

    public void display_data() {

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        // Query to select all profiles
        String select_data = "SELECT * FROM " + DBHelper.IMAGE_TABLE;

        Cursor sCursor = database.rawQuery(select_data, null);

        if (sCursor.moveToFirst()) {
            do {
                image_id.add(sCursor.getString(sCursor.getColumnIndex(DBHelper.IMAGE_ID)));
                image_name.add(sCursor.getString(sCursor.getColumnIndex(DBHelper.IMAGE_NAME)));
                image.add(sCursor.getBlob(sCursor.getColumnIndex(DBHelper.IMAGE_)));

                Log.v("Response:", " " + image_name + " " + image);

            } while (sCursor.moveToNext());
        }
        sCursor.close();
        adapter = new ProfileListAdapter(MainActivity.this, image_id, image_name, image);
        listView.setAdapter(adapter);
    }
}