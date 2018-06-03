package com.robpercival.demoapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ListView;

import com.robpercival.demoapp.R;
import com.robpercival.demoapp.adapters.RowReservationAdapter;
import com.robpercival.demoapp.presenter.MyReservationsPresenter;
import com.robpercival.demoapp.rest.dto.ReservationDTO;

import java.util.List;

public class MyReservationsActivity extends AppCompatActivity implements MyReservationsPresenter.MyReservationsView {

    private MyReservationsPresenter presenter;
    private List<ReservationDTO> reservations;
    private ListView reservationsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);



        presenter = new MyReservationsPresenter(this);
        presenter.getAllReservationsForUser();
    }



    @Override
    public void onGetReservationsFail(String s) {
        
    }

    @Override
    public void onGetReservationsSuccess() {

    }

    @Override
    public void onPopulateReservations(List<ReservationDTO> reservations) {

        this.reservations = reservations;
        reservationsListView = findViewById(R.id.reservationsListView);
        // izvuci iz bundle listu
        //
        /*List<String> stockList = new ArrayList<String>();
        stockList.add("stock1");
        stockList.add("stock2");

        String[] stockArr = new String[stockList.size()];
        stockArr = stockList.toArray(stockArr);*/
        //ReservationResponseDTO[] dtoArray = new ReservationResponseDTO[this.dtos.size()];
        //ReservationResponseDTO[] dtoArray =  (ReservationResponseDTO[]) this.dtos.toArray( new ReservationResponseDTO[this.dtos.size()]);

        RowReservationAdapter adapter = new RowReservationAdapter(this, this.reservations);
        reservationsListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onCancelClicked(ReservationDTO dto) {
        presenter.onCancelClicked(dto);
    }
}
