package com.example.harshad.image_crud;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class AddUpdateActivity extends AppCompatActivity {
    ImageView img1;
    EditText edt1;
    Button btn1;
    int SELECT_PICTURE = 1;
    SQLiteDatabase db;
    DBHelper mHelper;
    String selectedImagePath;
    byte[] byteImage = null;
    Intent intent;
    boolean isUpdate;
    String id, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        // the helper class for DB creation operation
        mHelper = new DBHelper(this);
        img1 = (ImageView) findViewById(R.id.imageView);
        edt1 = (EditText) findViewById(R.id.editText);
        btn1 = (Button) findViewById(R.id.button);

        isUpdate = getIntent().getBooleanExtra("update", false);
        if (isUpdate) {
            id = getIntent().getStringExtra("id");
            byteImage = getIntent().getByteArrayExtra("image");
            data = getIntent().getStringExtra("name");

            // Decoding Bitmap from stored ByteArray from preview the stored image
            img1.setImageBitmap(BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length));
            edt1.setText(data);
            btn1.setText("Update");
        }

        // Onclick event to select the image from gallery
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_PICTURE);
            }
        });


        // Onclick event to do insert or update the data based on isUpdate
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = edt1.getText().toString();
                if (data.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter Name!", Toast.LENGTH_SHORT).show();
                } else {
                    if (isUpdate) {
                        updateData(data, id);
                    } else {
                        saveData(data);
                    }
                }
            }
        });
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                selectedImagePath = cursor.getString(column_index);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >=REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath, options);
                // Preview for Selected Image
                img1.setImageBitmap(bitmap);
            }
        }
    }



    // Function for insertion
    private void saveData(String data) {
        db = mHelper.getWritableDatabase();
        // the helper class for doing insert operation
        InsertHelper ins = new InsertHelper(this);
        ins.open();

        try {
            // Encoding the Selected Image into ByteArray
            if (selectedImagePath != null) {
                FileInputStream in_stream = new FileInputStream(selectedImagePath);
                BufferedInputStream bif = new BufferedInputStream(in_stream);
                byteImage = new byte[bif.available()];
                bif.read(byteImage);
            } else {
                Toast.makeText(getApplicationContext(), "Please Select Image!", Toast.LENGTH_SHORT).show();
            }

            if (byteImage != null) {
                // Function call to insert data
                long ret = ins.insert_profile(byteImage, data);
                Log.e("saveData: ",byteImage+"" );
                Log.e("saveData: ",data );
                if (ret > 0) {
                    Toast.makeText(getApplicationContext(), "Image Saved in DB successfully.!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this.getBaseContext(), "Image Saved in DB successfully.", Toast.LENGTH_SHORT).show();
                    intent = new Intent(AddUpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this.getBaseContext(), "Image Saved in DB successfully.", Toast.LENGTH_SHORT).show();
                    intent = new Intent(AddUpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error Exception!", Toast.LENGTH_SHORT).show();
        }
        ins.close();
        db.close();
    }

    // Function for Updating the already stored value
    private void updateData(String data, String id) {
        db = mHelper.getWritableDatabase();

        UpdateHelper upd = new UpdateHelper(this);
        upd.open();

        try {
            // Encoding the Selected Image into ByteArray
            if (selectedImagePath != null) {
                FileInputStream in_stream = new FileInputStream(selectedImagePath);
                BufferedInputStream bif = new BufferedInputStream(in_stream);
                byteImage = new byte[bif.available()];
                bif.read(byteImage);
            }

            // Function call to update data
            long ret = upd.update_profile(id, byteImage, data);

            if (ret > 0) {
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                Toast.makeText(this.getBaseContext(), "Image Saved in DB successfully.", Toast.LENGTH_SHORT).show();
                intent = new Intent(AddUpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this.getBaseContext(), "Image Saved in DB successfully.", Toast.LENGTH_SHORT).show();
                intent = new Intent(AddUpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error Exception!", Toast.LENGTH_SHORT).show();
        }
        upd.close();
        db.close();
    }
}
