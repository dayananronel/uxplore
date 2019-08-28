package com.example.uxplore.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.uxplore.R;
import com.example.uxplore.SplashScreenActivity;
import com.example.uxplore.Utils.CommonFunctions;
import com.example.uxplore.Utils.RetrofitBuild;
import com.example.uxplore.Utils.ScreenNotification;
import com.example.uxplore.adapters.ChosenDestinationsRecyclerViewAdapter;
import com.example.uxplore.api.CommonAPI;
import com.example.uxplore.model.PackageDetails;
import com.example.uxplore.response.GenericResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChosenDestinationsActivity extends AppCompatActivity {

    private ArrayList<String> mPlace = new ArrayList<>();
    private ArrayList<String> mAddress = new ArrayList<>();
    ProgressDialog progressDialog;
    ImageView imageView;
    String packageid;
    private ScreenNotification screenNotification;
    private String status,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_proceed);

        progressDialog = new ProgressDialog(this);
        screenNotification = new ScreenNotification();
        imageView = findViewById(R.id.img_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChosenDestinationsActivity.this,MainActivity.class));
            }
        });

       // initImageBitmaps();
        packageid = getIntent().getStringExtra("packageid");
        Log.d("okhttp",packageid);
        getPackages();

    }

    private void initImageBitmaps() {
        mPlace.add("Temple of Leah");
        mAddress.add("Cebu City, Cebu");

        mPlace.add("Simala");
        mAddress.add("Sibonga City, Cebu");

        mPlace.add("E-Cubana");
        mAddress.add("Cebu City");

        mPlace.add("Temple of Leah");
        mAddress.add("Cebu City, Cebu");

        mPlace.add("Simala");
        mAddress.add("Sibonga City, Cebu");

        mPlace.add("E-Cubana");
        mAddress.add("Cebu City");
        mPlace.add("Temple of Leah");
        mAddress.add("Cebu City, Cebu");

        mPlace.add("Simala");
        mAddress.add("Sibonga City, Cebu");

        mPlace.add("E-Cubana");
        mAddress.add("Cebu City");

        initRecyclerView();

    }
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.booking_proceed_chosen_des_recyclerView);
        ChosenDestinationsRecyclerViewAdapter packageListRecyclerViewAdapter =
                new ChosenDestinationsRecyclerViewAdapter(getApplicationContext(),mPlace,mAddress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(packageListRecyclerViewAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();

                progressDialog.setTitle("Find Driver");
                progressDialog.setMessage("Finding you a driver... Please wait..");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }
        }, 2000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();

                startActivity(new Intent(ChosenDestinationsActivity.this,FindNearbyDriverActivity.class));
                finish();

            }
        }, 5000);

    }

    private void getPackages() {
        if (CommonFunctions.getConnectivityStatus(ChosenDestinationsActivity.this) > 0) {
//            progressDialog.setTitle("Getting Choosen Destinations...");
            progressDialog.setMessage("Getting Choosen Destinations... Please wait..");
            progressDialog.setCancelable(false);
            progressDialog.show();
            //Validate Entry
            try {

                CommonAPI api = RetrofitBuild.getClient().create(CommonAPI.class);
                Call<GenericResponse> responseCall = api.packageDetails(packageid);
                responseCall.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        progressDialog.dismiss();

                        status = String.valueOf(response.body().getStatus());
                        message = String.valueOf(response.body().getMessage());


                        Log.d("okhttp","STAT: "+status);
                        Log.d("okhttp","Message: "+message);
                        Log.d("okhttp","DATA: "+ response.body().getData());

                        Log.d("okhttp","RESPONSE BODY: "+ response.toString());

                        switch (status){
                            case "000" :case "200":

                                String json = new Gson().toJson(response.body().getData());
                                Log.d("okhttp","DATAAAA: "+json);
                                //LIST OF OBJECT
                                List<PackageDetails> destinationsList = new Gson().fromJson(json, new TypeToken<List<PackageDetails>>() {
                                }.getType());
                                //OBJECT
                                for (PackageDetails aDestinations : destinationsList) {
                                    mPlace.add(aDestinations.getSpotName());
                                    mAddress.add(aDestinations.getSpotAddress());
                                }
                                initRecyclerView();
                                break;

                            default:
                                AlertDialog alertDialog1 = new AlertDialog.Builder(ChosenDestinationsActivity.this)
                                        //set icon
                                        .setIcon(R.drawable.ic_error_red_900_36dp)
                                        //set title
                                        .setTitle("Initialization Failed")
                                        .setCancelable(false)
                                        //set message
                                        .setMessage(message)
                                        //set positive button
                                        .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //set what would happen when positive button is clicked
                                                dialogInterface.dismiss();
                                            }
                                        })
                                        .show();

                                break;
                        }


                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.d("okhttp","ERROR : "+t.getMessage());
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            progressDialog.dismiss();
            screenNotification.showToast(this,"No Internet Connection.");
        }
    }

}
