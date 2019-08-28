package com.example.uxplore.activities;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uxplore.Utils.CommonFunctions;
import com.example.uxplore.Utils.DBHandler;
import com.example.uxplore.Utils.RetrofitBuild;
import com.example.uxplore.Utils.ScreenNotification;
import com.example.uxplore.Utils.SharedPref;
import com.example.uxplore.adapters.PackageListRecyclerViewAdapter;
import com.example.uxplore.R;
import com.example.uxplore.api.CommonAPI;
import com.example.uxplore.model.Destinations;
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

public class PackageDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;

    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mDescription = new ArrayList<>();
    private ArrayList<String> mEntranceAdult = new ArrayList<>();
    private ArrayList<String> mEntranceSenior = new ArrayList<>();
    private ArrayList<String> mEntranceKids = new ArrayList<>();
    private ArrayList<String> mSched = new ArrayList<>();
    private ArrayList<String> mSpotFee = new ArrayList<>();

    private Button btnAvail,btnCancel;
    private TextView label,package_totalFee,packageid;
    DBHandler dbHandler ;
    
    private ScreenNotification screenNotification;
    private String status,message;
    private double total = 0.00;
    String packageidValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);

        imageView = findViewById(R.id.img_back);
        btnAvail = findViewById(R.id.package_btn_avail);
        btnCancel = findViewById(R.id.package_btn_cancel);
        label = findViewById(R.id.toolbar_packageName);
        package_totalFee = findViewById(R.id.package_totalFee);
        packageid = findViewById(R.id.toolbar_packageID);

        btnAvail.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        label.setText(getIntent().getStringExtra("packagename"));
        packageid.setText(getIntent().getStringExtra("spotid"));

        packageidValue = getIntent().getStringExtra("spotid");

        Log.d("okhttp","PACKAGE Name: "+getIntent().getStringExtra("packagename"));
        Log.d("okhttp","PACKAGE ID: "+packageidValue);

        screenNotification = new ScreenNotification();
        
        dbHandler = new DBHandler(PackageDetailsActivity.this);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(PackageDetailsActivity.this,MainActivity.class));
                finish();

            }
        });
        getPackages();
    }

    private void initImageBitmaps() {
        mImageUrls.add("https://www.1zoom.me/big2/21/326668-svetik.jpg");
        mNames.add("Colon");
        mDescription.add(getString(R.string.mountanious_region_with_a_visit_expense_of_flowers_inclucing_an_area_known_as_mini_amsterdam));
        mSpotFee.add("");
        //mEntranceAdult.add("Adults: P"+20);
        //mEntranceSenior.add("Senior Citizen: P"+15);
       // mEntranceKids.add("Kids: P"+10);
       // mSched.add("Mon-Sat: 7am-7pm");


        initRecyclerView();

    }
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.package_details_recyclerView);
        PackageListRecyclerViewAdapter packageListRecyclerViewAdapter =
                new PackageListRecyclerViewAdapter(getApplicationContext(),mNames,mImageUrls,mDescription,mSpotFee);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(packageListRecyclerViewAdapter);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.package_btn_avail:

                Cursor cursor = dbHandler.getAllData();

               if(cursor.getCount() <= 0){
                   for(int x=0;x<mNames.size();x++){
                       dbHandler.insertUserDetails(label.getText().toString(),mNames.get(x),String.valueOf(total),packageidValue);
                   }
                   Toast.makeText(getApplicationContext(),"Package added for booking.",Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(this,MainActivity.class));
                   finish();
               }else{
                   Toast.makeText(getApplicationContext(),"You already chosen a package",Toast.LENGTH_SHORT).show();
               }
                break;
            case R.id.package_btn_cancel:
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PackageDetailsActivity.this,MainActivity.class));
        finish();
    }


    private void getPackages() {
        if (CommonFunctions.getConnectivityStatus(PackageDetailsActivity.this) > 0) {
            screenNotification.progressDialog(PackageDetailsActivity.this, "Please Wait", "Getting Packages...");
            //Validate Entry
            try {

                CommonAPI api = RetrofitBuild.getClient().create(CommonAPI.class);
                Call<GenericResponse> responseCall = api.packageDetails(packageidValue);
                responseCall.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        screenNotification.hideProgressDialog(PackageDetailsActivity.this);

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
                                    mImageUrls.add(aDestinations.getImageURL());
                                    mNames.add(aDestinations.getSpotName());
                                    mDescription.add(aDestinations.getSpotDescription());
                                    mSpotFee.add(aDestinations.getSpotRate());

                                    total += Double.parseDouble(aDestinations.getSpotRate());

                                }

                                DecimalFormat formatter = new DecimalFormat("#,###.00");
                                System.out.println(formatter.format(total));

                                package_totalFee.setText(formatter.format(total));
                                initRecyclerView();
                                break;

                            default:
                                AlertDialog alertDialog1 = new AlertDialog.Builder(PackageDetailsActivity.this)
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
                        screenNotification.hideProgressDialog(PackageDetailsActivity.this);
                        Log.d("okhttp","ERROR : "+t.getMessage());
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            screenNotification.hideProgressDialog(PackageDetailsActivity.this);
            screenNotification.showToast(this,"No Internet Connection.");
        }
    }



}
