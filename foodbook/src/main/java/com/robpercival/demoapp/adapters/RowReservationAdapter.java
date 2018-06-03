package com.robpercival.demoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.robpercival.demoapp.R;
import com.robpercival.demoapp.activities.MyReservationsActivity;
import com.robpercival.demoapp.rest.dto.ReservationDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 6/3/2018.
 */

public class RowReservationAdapter extends BaseAdapter {

    private Context myReservationsActivity;
    private List<ReservationDTO> data;
    private static LayoutInflater inflater = null;

    public RowReservationAdapter(Context myReservationsActivity, List<ReservationDTO> data) {
        this.myReservationsActivity = myReservationsActivity;
        if(data == null) {
            data = new ArrayList<>();
        }
        this.data = data;
        inflater = (LayoutInflater) myReservationsActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row_reservation, null);

        final ReservationDTO dto = data.get(position);

        Button cancelButton = vi.findViewById(R.id.cancelMyReservationButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyReservationsActivity) myReservationsActivity).onCancelClicked(dto);
            }
        });
/*
        private Date beginDate;
        private Date endDate;
        private String owner;
        private String restaurantName;

    */
        TextView restaurantNameTextView = vi.findViewById(R.id.restaurantNameTextView);

        restaurantNameTextView.setText(dto.getRestaurantName());
        TextView beginDateTextView = vi.findViewById(R.id.beginDateTextView);
        beginDateTextView.setText(dto.getBeginDate().toString());

        TextView ownerTextView = vi.findViewById(R.id.ownerTextView);
        ownerTextView.setText(dto.getOwner());

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
