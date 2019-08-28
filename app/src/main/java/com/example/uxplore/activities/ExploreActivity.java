package com.example.uxplore.activities;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.uxplore.Utils.CommonFunctions;
import com.example.uxplore.Utils.RetrofitBuild;
import com.example.uxplore.Utils.ScreenNotification;
import com.example.uxplore.adapters.GenericRecyclerViewAdapter;
import com.example.uxplore.R;
import com.example.uxplore.api.CommonAPI;
import com.example.uxplore.model.Destinations;
import com.example.uxplore.response.GenericResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreActivity extends AppCompatActivity {

    //variables
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mSpotID = new ArrayList<>();
    private ImageView imgBack;
    
    private ScreenNotification screenNotification;
    private String status,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        screenNotification = new ScreenNotification();
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExploreActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });
        
        getDestinations();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.explore_recylcerView);
        GenericRecyclerViewAdapter genericRecyclerViewAdapter = new GenericRecyclerViewAdapter(getApplicationContext(),mNames,mImageUrls,mSpotID);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(genericRecyclerViewAdapter);
    }

    private void getDestinations() {
        if (CommonFunctions.getConnectivityStatus(ExploreActivity.this) > 0) {
            screenNotification.progressDialog(ExploreActivity.this, "Please Wait", "Getting Destinations...");
            //Validate Entry
            try {

                CommonAPI api = RetrofitBuild.getClient().create(CommonAPI.class);
                Call<GenericResponse> responseCall = api.destinations(CommonFunctions.getLocalIpAddress());
                responseCall.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        screenNotification.hideProgressDialog(ExploreActivity.this);

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
                                List<Destinations> destinationsList = new Gson().fromJson(json, new TypeToken<List<Destinations>>() {
                                }.getType());
                                //OBJECT
                                for (Destinations aDestinations : destinationsList) {
                                    mImageUrls.add(aDestinations.getImageURL());
                                    mNames.add(aDestinations.getName());
                                    mSpotID.add(aDestinations.getSpotID());

                                }
                                initRecyclerView();
                                break;

                            default:
                                AlertDialog alertDialog1 = new AlertDialog.Builder(ExploreActivity.this)
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
                        screenNotification.hideProgressDialog(ExploreActivity.this);
                        Log.d("okhttp","ERROR : "+t.getMessage());

                        AlertDialog alertDialog1 = new AlertDialog.Builder(ExploreActivity.this)
                                //set icon
                                .setIcon(R.drawable.ic_error_red_900_36dp)
                                //set title
                                .setTitle("Initialization Failed")
                                .setCancelable(false)
                                //set message
                                .setMessage("Connection time out. Please try again.")
                                //set positive button
                                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //set what would happen when positive button is clicked
                                        dialogInterface.dismiss();
                                        getDestinations();
                                    }
                                })
                                .show();

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            screenNotification.hideProgressDialog(ExploreActivity.this);
            screenNotification.showToast(this,"No Internet Connection.");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ExploreActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
