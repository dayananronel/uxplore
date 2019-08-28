package com.example.uxplore.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.uxplore.R;
import com.example.uxplore.adapters.TourHistoryRecyclerViewAdapter;

import java.util.ArrayList;

public class TourHistoryActivity extends AppCompatActivity {

    private ArrayList<String> mStatus = new ArrayList<>();
    private ArrayList<String> mDate = new ArrayList<>();
    private ArrayList<String> mLocationPre = new ArrayList<>();
    private ArrayList<String> mLocationPost = new ArrayList<>();
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_history);

        initImageBitmaps();

        imageView = findViewById(R.id.img_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TourHistoryActivity.this,MainActivity.class));
                finish();

            }
        });

    }
    private void initImageBitmaps() {
        mStatus.add("Pending");
        mDate.add("8:30 am July 8, 2019");
        mLocationPre.add("Urgello, Cebu City");
        mLocationPost.add("Talamban, Cebu City");

        mStatus.add("Completed");
        mDate.add("8:30 am July 8, 2019");
        mLocationPre.add("Urgello, Cebu City");
        mLocationPost.add("Talamban, Cebu City");

        mStatus.add("On Process");
        mDate.add("8:30 am July 8, 2019");
        mLocationPre.add("Urgello, Cebu City");
        mLocationPost.add("Talamban, Cebu City");

        mStatus.add("On Process");
        mDate.add("8:30 am July 8, 2019");
        mLocationPre.add("Urgello, Cebu City");
        mLocationPost.add("Talamban, Cebu City");

        mStatus.add("On Process");
        mDate.add("8:30 am July 8, 2019");
        mLocationPre.add("Urgello, Cebu City");
        mLocationPost.add("Talamban, Cebu City");

        mStatus.add("On Process");
        mDate.add("8:30 am July 8, 2019");
        mLocationPre.add("Urgello, Cebu City");
        mLocationPost.add("Talamban, Cebu City");

        mStatus.add("On Process");
        mDate.add("8:30 am July 8, 2019");
        mLocationPre.add("Urgello, Cebu City");
        mLocationPost.add("Talamban, Cebu City");


        initRecyclerView();

    }
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.tourist_recyclerView);
        TourHistoryRecyclerViewAdapter packageListRecyclerViewAdapter =
                new TourHistoryRecyclerViewAdapter(getApplicationContext(),mStatus,mDate,mLocationPre,mLocationPost);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(packageListRecyclerViewAdapter);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TourHistoryActivity.this,MainActivity.class));
        finish();
    }
}
