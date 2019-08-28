package com.example.uxplore.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.uxplore.R;
import com.example.uxplore.Utils.CommonFunctions;
import com.example.uxplore.Utils.FavoritesHandler;
import com.example.uxplore.Utils.RetrofitBuild;
import com.example.uxplore.Utils.ScreenNotification;
import com.example.uxplore.adapters.GenericRecyclerViewAdapter;
import com.example.uxplore.adapters.PackageListRecyclerViewAdapter;
import com.example.uxplore.api.CommonAPI;
import com.example.uxplore.model.Destinations;
import com.example.uxplore.response.GenericResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView details_favorites;
    private TextView details_name,details_description,details_address,details_rate;
    private RatingBar ratingBar;
    private ImageView imageDetails;
    private FavoritesHandler favoritesHandler;
    String value,spotid;
    private ScreenNotification screenNotification;
    private String status,message,imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        value = intent.getStringExtra("packagename");
        spotid = intent.getStringExtra("spotid");

        Log.d("okhttp","DATA: "+value+spotid);


        screenNotification = new ScreenNotification();
        init();


        getDestinations();

    }

    private void init() {
        details_favorites = findViewById(R.id.details_favorites);
        details_name = findViewById(R.id.details_name);
        details_description = findViewById(R.id.details_description);
        details_address = findViewById(R.id.details_address);
        details_rate = findViewById(R.id.details_rate);
        ratingBar = findViewById(R.id.details_ratingBar);
        imageDetails = findViewById(R.id.image_package_details);

        favoritesHandler = new FavoritesHandler(DetailsActivity.this);

        details_name.setText(value);

        listener();
    }

    private void listener(){
        details_favorites.setOnClickListener(this);

        Cursor cursor = favoritesHandler.getData(details_name.getText().toString());
        if(cursor.getCount() > 0){
            details_favorites.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_700_36dp));
        }else{
            details_favorites.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_36dp));
        }
        cursor.close();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,ExploreActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.details_favorites:
                Cursor cursor = favoritesHandler.getData(details_name.getText().toString());
                if(cursor.getCount() <= 0){
                    details_favorites.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_700_36dp));
                    favoritesHandler.insertUserDetails(details_name.getText().toString(),imageurl);
                    Toast.makeText(getApplicationContext(),"Added to favorites.",Toast.LENGTH_SHORT).show();
                }else{
                    details_favorites.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_36dp));
                    Toast.makeText(getApplicationContext(),"Removed from favorites.",Toast.LENGTH_SHORT).show();
                    favoritesHandler.deleteSingle(details_name.getText().toString());
                }
                cursor.close();
            break;
        }
    }

    private void getDestinations() {
        if (CommonFunctions.getConnectivityStatus(DetailsActivity.this) > 0) {
            screenNotification.progressDialog(DetailsActivity.this, "Please Wait", "Getting Destination Data...");
            //Validate Entry
            try {

                CommonAPI api = RetrofitBuild.getClient().create(CommonAPI.class);
                Call<GenericResponse> responseCall = api.destination(spotid);
                responseCall.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        screenNotification.hideProgressDialog(DetailsActivity.this);

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

                                Destinations destinations = null;
                                //OBJECT
                                for (Destinations aDestinations : destinationsList) {
                                   destinations = aDestinations;
                                }

                                details_name.setText(destinations.getName());
                                details_description.setText(destinations.getDescription());
                                details_address.setText(destinations.getAddress());
                                details_rate.setText(CommonFunctions.currencyFormatter(destinations.getRate()));


                                Glide.with(getApplicationContext())
                                        .load(destinations.getImageURL())
                                        .placeholder(R.drawable.landingimage)
                                        .error(R.drawable.placeholder)
                                        .thumbnail(1)
                                        .into(imageDetails);

                                double percent = 0.05;
                                if(destinations.getRating() == null){
                                    float rate = (float) ((float)0 * percent);
                                    ratingBar.setRating(rate);
                                }else{
                                    double rating = Double.parseDouble(destinations.getRating());
                                    float rate = (float) ((float)rating * percent);
                                    ratingBar.setRating(rate);
                                }

                                break;

                            default:
                                AlertDialog alertDialog1 = new AlertDialog.Builder(DetailsActivity.this)
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
                        screenNotification.hideProgressDialog(DetailsActivity.this);
                        Log.d("okhttp","ERROR : "+t.getMessage());

                        AlertDialog alertDialog1 = new AlertDialog.Builder(DetailsActivity.this)
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
            screenNotification.hideProgressDialog(DetailsActivity.this);
            screenNotification.showToast(this,"No Internet Connection.");
        }
    }


}
