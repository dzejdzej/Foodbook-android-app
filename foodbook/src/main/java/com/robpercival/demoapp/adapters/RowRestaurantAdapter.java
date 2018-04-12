package com.robpercival.demoapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.robpercival.demoapp.R;

public class RowRestaurantAdapter extends BaseAdapter {

    private Context mainActivity;
    private String[] data;
    private static LayoutInflater inflater = null;

    public RowRestaurantAdapter(Context mainActivity, String[] data) {
        this.mainActivity = mainActivity;
        this.data = data;
        inflater = (LayoutInflater) mainActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row_restaurant, null);

        ImageView imageView = vi.findViewById(R.id.restaurantImageView);
        Drawable myDrawable = mainActivity.getResources().getDrawable(R.mipmap.restaurant);
        imageView.setImageDrawable(myDrawable);

        TextView restaurantNameTextView = vi.findViewById(R.id.restaurantNameTextView);
        restaurantNameTextView.setText("Restaurant Name Yeahh");

        RatingBar ratingBar = vi.findViewById(R.id.ratingBar);
        ratingBar.setRating(3.5f);

        TextView restaurantRatingTextView = vi.findViewById(R.id.restaurantRatingTextView);
        restaurantRatingTextView.setText("4 / 5");

        return vi;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
