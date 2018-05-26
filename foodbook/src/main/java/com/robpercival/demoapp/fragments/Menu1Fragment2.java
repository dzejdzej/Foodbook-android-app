package com.robpercival.demoapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ListView;

import com.robpercival.demoapp.R;
import com.robpercival.demoapp.activities.SingleRestaurantActivity;
import com.robpercival.demoapp.adapters.RowRestaurantAdapter;


public class Menu1Fragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu1, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Home");

        ListView restaurantListView = getView().findViewById(R.id.restaurantListView);

        restaurantListView = getView().findViewById(R.id.restaurantListView);
        restaurantListView.setAdapter(new RowRestaurantAdapter(getActivity(), new String[] { "data1",
                "data2", "data3", "data4" }));

        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GridLayout restaurantGridLayout = (GridLayout) view;

                // iscupati koji je restoran u pitanju
                Intent singleRestaurantIntent = new Intent(getActivity(), SingleRestaurantActivity.class);
                getActivity().startActivity(singleRestaurantIntent);
            }
        });
    }

}
