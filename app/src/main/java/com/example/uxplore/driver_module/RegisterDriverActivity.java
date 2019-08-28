package com.example.uxplore.driver_module;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uxplore.R;
import com.example.uxplore.Utils.CommonFunctions;
import com.example.uxplore.Utils.RetrofitBuild;
import com.example.uxplore.Utils.SharedPref;
import com.example.uxplore.activities.LoginActivity;
import com.example.uxplore.activities.MainActivity;
import com.example.uxplore.api.CommonAPI;
import com.example.uxplore.model.UserProfile;
import com.example.uxplore.response.GenericResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterDriverActivity extends AppCompatActivity implements View.OnClickListener {

    Button register_btn;
    ProgressDialog progressDialog;
    EditText et_firstName,et_lastName,et_contact,et_address,et_username,et_password;
    String firstName,lastName,contact,address,username,password,email;
    private String status;
    private String message;
    private EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        ImageView imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(RegisterDriverActivity.this, LoginActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                finish();
            }
        });

        init();

    }

    private void init() {
        register_btn = findViewById(R.id.register_btn);
        progressDialog = new ProgressDialog(this);

        //et_firstName = findViewById(R.id.register_firstName);
        //et_lastName = findViewById(R.id.register_lastName);
        et_contact = findViewById(R.id.register_contactNumber);
        //et_address= findViewById(R.id.register_currentAddress);
        //et_username = findViewById(R.id.register_username);
        et_password = findViewById(R.id.register_password);
        et_email= findViewById(R.id.register_email);
        listener();

    }

    private void listener() {
        register_btn.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn:
                validateFields();
                break;
        }
    }

    private void validateFields() {
       // firstName = et_firstName.getText().toString();
        //lastName = et_lastName.getText().toString();
        //address = et_address.getText().toString();
        contact = et_contact.getText().toString();
        //username = et_username.getText().toString();
        password = et_password.getText().toString();
        email = et_email.getText().toString();

        if(contact.isEmpty()  || password.isEmpty() || email.isEmpty()){
            Toast.makeText(getApplicationContext(),"All Fields are mandatory.",Toast.LENGTH_SHORT).show();
        }else{
           if(contact.length() < 11){
               et_contact.setError("Invalid Mobile Number.");
               et_contact.requestFocus();
               et_contact.setFocusableInTouchMode(true);
           }if(!CommonFunctions.isValidEmail(email)){
                et_email.setError("Invalid Email.");
                et_email.requestFocus();
                et_email.setFocusableInTouchMode(true);
            }
           else{
               showCustomDialog();
           }
        }

    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_register_requirements, viewGroup, false);
        //Now we need an AlertDialog.Builder object

        Button confirm = dialogView.findViewById(R.id.register_inprocess_btn);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                progressDialog.setTitle("Register");
                progressDialog.setMessage("Please wait for a moment...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                registerUser();

            }
        });

    }

    private void registerUser() {
        if(CommonFunctions.getConnectivityStatus(getApplicationContext())>0){

            //Validate Entry
            try {

                CommonAPI api = RetrofitBuild.getClient().create(CommonAPI.class);
                Call<GenericResponse> responseCall = api.register("TOURIST",email,contact,password,getLocalIpAddress());
                responseCall.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

                        progressDialog.dismiss();

                        status = String.valueOf(Objects.requireNonNull(response.body()).getStatus());
                        message = String.valueOf(response.body().getMessage());


                        Log.d("okhttp","STAT: "+status);
                        Log.d("okhttp","Message: "+message);
                        Log.d("okhttp","DATA: "+ response.body().getData());

                        Log.d("okhttp","RESPONSE BODY: "+ response.toString());

                        switch (status){
                            case "000":

                                Toast.makeText(getApplicationContext(),"Registered Successfully",Toast.LENGTH_SHORT).show();

                                SharedPref.saveLoginStatus(getApplicationContext(),"true");

                                Intent intent = new Intent(RegisterDriverActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);


                                break;

                            default:
                                AlertDialog alertDialog1 = new AlertDialog.Builder(RegisterDriverActivity.this)
                                        //set icon
                                        .setIcon(R.drawable.ic_error_red_900_36dp)
                                        //set title
                                        .setTitle("Register Failed")
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
                progressDialog.dismiss();
                e.printStackTrace();
            }


        }else{
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"No Internet Connection.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(RegisterDriverActivity.this, LoginActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
        finish();
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
