package com.example.uxplore.activities;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.uxplore.R;
import com.example.uxplore.model.AccountItemObject;

public class FindNearbyDriverActivity extends AppCompatActivity {

    RatingBar ratingBar;
    ImageView imgBack,imageView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_nearby_driver);
        imgBack = findViewById(R.id.img_back);

        imageView = findViewById(R.id.findNearbyGuide_photo);
        ratingBar = findViewById(R.id.driver_rating);

        progressDialog = new ProgressDialog(FindNearbyDriverActivity.this);


        progressDialog.setTitle("Found Driver");
        progressDialog.setMessage("Please wait for a moment..");
        progressDialog.setCancelable(false);
        progressDialog.show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
               showCustomDialog();

            }
        }, 3000);


        Glide.with(this)
                .load(R.drawable.ronel)
                .override(250,250)
                .centerCrop()
                .transform(new CircleCrop())
                .into(imageView);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindNearbyDriverActivity.this,MainActivity.class));
                finish();

            }
        });

    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.driver_arrive_layout, viewGroup, false);
        //Now we need an AlertDialog.Builder object

        Button confirm = dialogView.findViewById(R.id.okay_btn_arrive_driver);
        ImageView imageView = dialogView.findViewById(R.id.driver_photo);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        Glide.with(this)
                .load(R.drawable.ronel)
                .override(250,250)
                .centerCrop()
                .transform(new CircleCrop())
                .into(imageView);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                showCustomDialog1();

            }
        });

    }

    private void showCustomDialog1() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.driver_rating_layout, viewGroup, false);
        //Now we need an AlertDialog.Builder object

        Button submit = dialogView.findViewById(R.id.submit_btn_rate_driver);
        ImageView imageView = dialogView.findViewById(R.id.driver_photo);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        Glide.with(this)
                .load(R.drawable.ronel)
                .override(250,250)
                .centerCrop()
                .transform(new CircleCrop())
                .into(imageView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                startActivity(new Intent(FindNearbyDriverActivity.this,MainActivity.class));

            }
        });

    }
}
