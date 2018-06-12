package com.robpercival.demoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.robpercival.demoapp.R;
import com.robpercival.demoapp.rest.dto.CommentDto;
import com.robpercival.demoapp.sqlite.MyReservation;

import java.util.ArrayList;
import java.util.List;

public class MyOfflineReservationAdapter extends BaseAdapter {

    private Context offlineMyReservations;
    private List<MyReservation> data;
    private static LayoutInflater inflater = null;

    public MyOfflineReservationAdapter(Context offlineMyReservations, List<MyReservation> data) {
        this.offlineMyReservations = offlineMyReservations;
        if(data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
        inflater = (LayoutInflater) offlineMyReservations
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row_offline_reservation, null);

        final MyReservation myReservation = data.get(position);

        TextView commentOwnerName = vi.findViewById(R.id.beginDateTextView);
        commentOwnerName.setText(myReservation.getReservationDate());

        TextView commentText = vi.findViewById(R.id.restaurantName);
        commentText.setText(myReservation.getRestaurantName());


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
