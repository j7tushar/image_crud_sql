package com.example.harshad.image_crud;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Harshad on 09/09/2016.
 */
public class ProfileListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<String> image_id;
    ArrayList<String> image_name;
    ArrayList<byte[]> image;
    LayoutInflater layoutInflater;
    ImageView profile;
    TextView name;
    byte[] bytes;

    public ProfileListAdapter(Context mContext, ArrayList image_id, ArrayList image_name, ArrayList image) {
        this.mContext = mContext;
        this.image_id = image_id;
        this.image_name = image_name;
        this.image = image;
    }

    public int getCount() {
        return image_id.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        layoutInflater = ((Activity) mContext).getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.list_item_profile, null);

        profile = (ImageView) convertView.findViewById(R.id.profile_image);
        name = (TextView) convertView.findViewById(R.id.profile_name);

        name.setText(image_name.get(position));
        bytes = image.get(position);
        // Decoding Bitmap from stored ByteArray
        profile.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));

        return convertView;
    }
}
