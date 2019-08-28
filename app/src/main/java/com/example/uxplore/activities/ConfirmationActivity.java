package com.example.uxplore.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uxplore.R;
import com.example.uxplore.Utils.CommonFunctions;
import com.example.uxplore.Utils.RetrofitBuild;
import com.example.uxplore.Utils.ScreenNotification;
import com.example.uxplore.Utils.SharedPref;
import com.example.uxplore.api.CommonAPI;
import com.example.uxplore.model.PaymentsCallBack;
import com.example.uxplore.response.GenericResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class ConfirmationActivity extends AppCompatActivity {

    private ScreenNotification screenNotification;
    private String userid,transactionstatus,paymentid,paymentamout,payment_type,driverid;
    private String status,message;
    JSONObject jsonDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        screenNotification = new ScreenNotification();
        //Getting Intent
        Intent intent = getIntent();
        try {
             jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));
            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
            paymentid = jsonDetails.getString("id");
            transactionstatus = jsonDetails.getString("state");

        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {
        //Views
        TextView textViewId = (TextView) findViewById(R.id.paymentId);
        TextView textViewStatus= (TextView) findViewById(R.id.paymentStatus);
        TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);
        Button confirm_btn = findViewById(R.id.confirm_btn);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewStatus.getText().toString().contains("approved")){
                    transaction();
                }else{
                    finish();
                }
            }
        });
        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText(paymentAmount+" Php");
    }


    @Override
    public void onBackPressed() {
        
    }

    private void transaction() {
        if (CommonFunctions.getConnectivityStatus(ConfirmationActivity.this) > 0) {
            screenNotification.progressDialog(ConfirmationActivity.this, "Please Wait", "Processing transaction...");

            //get value
            userid = SharedPref.getUserID(ConfirmationActivity.this);
            paymentid = getIntent().getStringExtra("packageid");
            payment_type = "PAYPAL";

            //Validate Entry
            try {

                CommonAPI api = RetrofitBuild.getClient().create(CommonAPI.class);
                Call<GenericResponse> responseCall = api.transaction(paymentid,paymentamout,paymentamout,payment_type,userid,transactionstatus,driverid);
                responseCall.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        screenNotification.hideProgressDialog(ConfirmationActivity.this);

                        status = String.valueOf(response.body().getStatus());
                        message = String.valueOf(response.body().getMessage());


                        Log.d("okhttp","STAT: "+status);
                        Log.d("okhttp","Message: "+message);
                        Log.d("okhttp","DATA: "+ response.body().getData());

                        Log.d("okhttp","RESPONSE BODY: "+ response.toString());

                        switch (status){
                            case "000":

                                String json = new Gson().toJson(response.body().getData());
                                Log.d("okhttp","DATAAAA: "+json);


                                PaymentsCallBack paymentsCallBack;
                                //LIST OF OBJECT
                                List<PaymentsCallBack> paymentsCallBacks = new Gson().fromJson(json, new TypeToken<List<PaymentsCallBack>>() {
                                }.getType());
                                //OBJECT
                                for (PaymentsCallBack schoolUserProfile : paymentsCallBacks) {
                                    paymentsCallBack = schoolUserProfile;
                                    Log.d("okhttp",paymentsCallBack.getTxnno());
                                }

                                AlertDialog alertDialog = new AlertDialog.Builder(ConfirmationActivity.this)
                                        //set icon
                                        .setIcon(R.drawable.ic_local_airport_light_blue_400_48dp)
                                        //set title
                                        .setTitle("UXplore")
                                        .setCancelable(false)
                                        //set message
                                        .setMessage("Transaction Successful")
                                        //set positive button
                                        .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //set what would happen when positive button is clicked
                                                dialogInterface.dismiss();
                                                try {
                                                    startActivity(new Intent(ConfirmationActivity.this,ChosenDestinationsActivity.class)
                                                            .putExtra("paymentid",jsonDetails.getString("id"))
                                                            .putExtra("packageid",getIntent().getStringExtra("packageid")));
                                                    SharedPref.savePaymentID(getApplicationContext(),jsonDetails.getString("id"));
                                                    SharedPref.saveAmountConsumed(getApplicationContext(),paymentamout);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        })
                                        .show();
                                break;

                            default:
                                AlertDialog alertDialog1 = new AlertDialog.Builder(ConfirmationActivity.this)
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
                        screenNotification.hideProgressDialog(ConfirmationActivity.this);
                        Log.d("okhttp","ERROR : "+t.getMessage());



                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            screenNotification.hideProgressDialog(ConfirmationActivity.this);
            screenNotification.showToast(ConfirmationActivity.this,"No Internet Connection.");
        }
    }
    
}
