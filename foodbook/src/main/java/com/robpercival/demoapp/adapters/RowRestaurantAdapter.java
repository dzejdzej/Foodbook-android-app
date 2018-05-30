package com.robpercival.demoapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.robpercival.demoapp.R;
import com.robpercival.demoapp.rest.dto.user.ReservationResponseDTO;

import java.util.List;

public class RowRestaurantAdapter extends BaseAdapter {

    private Context mainActivity;
    private List<ReservationResponseDTO> data;
    private static LayoutInflater inflater = null;

    public RowRestaurantAdapter(Context mainActivity, List<ReservationResponseDTO> data) {
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

        ReservationResponseDTO dto = data.get(position);
        dto.setImageUrl("img/mcdonalds.png");
        ImageView imageView = vi.findViewById(R.id.restaurantImageView);
        Drawable myDrawable = mainActivity.getResources().getDrawable(R.drawable.restaurant);
        Glide.with(this.mainActivity)
                .load("http://192.168.0.48:8080/" + dto.getImageUrl())
                .into(imageView);
        //imageView.setImageDrawable(myDrawable);

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
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
