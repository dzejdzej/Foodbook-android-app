package com.robpercival.demoapp.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ListView;

import com.robpercival.demoapp.R;
import com.robpercival.demoapp.adapters.RowRestaurantAdapter;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView restaurantListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDrawerAndToolbar();
        populateListView();

        System.out.println(getIntent().getExtras().get("location") + "");
        System.out.println(getIntent().getExtras().get("cuisine") + "");
    }

    private void populateListView() {
        restaurantListView = findViewById(R.id.restaurantListView);

        restaurantListView = findViewById(R.id.restaurantListView);
        restaurantListView.setAdapter(new RowRestaurantAdapter(this, new String[] { "data1",
                "data2", "data3", "data4" }));

        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GridLayout restaurantGridLayout = (GridLayout) view;

                // iscupati koji je restoran u pitanju
                Intent singleRestaurantIntent = new Intent(MainActivity.this, SingleRestaurantActivity.class);
                MainActivity.this.startActivity(singleRestaurantIntent);
            }
        });
    }

    private void setupDrawerAndToolbar() {
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu40x40);

        this.drawerLayout = findViewById(R.id.drawer_layout);

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(),R.drawable.user40x40);
        toolbar.setOverflowIcon(drawable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
