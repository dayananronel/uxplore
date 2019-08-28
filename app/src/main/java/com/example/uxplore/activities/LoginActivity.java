package com.example.uxplore.activities;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uxplore.Utils.CommonFunctions;
import com.example.uxplore.R;
import com.example.uxplore.Utils.ScreenNotification;
import com.example.uxplore.Utils.SharedPref;
import com.example.uxplore.api.CommonAPI;
import com.example.uxplore.driver_module.RegisterDriverActivity;
import com.example.uxplore.Utils.RetrofitBuild;
import com.example.uxplore.model.UserProfile;
import com.example.uxplore.response.CommonResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{

    EditText et_mobileNumber,et_mobilePassword,et_login_email,et_email_password;
    Button btn_signIn,btn_email_signIn;
    TextView login_tv_useEmailAdd,login_tv_forgotPassword, login_tv_Register;
    LinearLayout login_mobile_layout,login_useEmailAdd_layout;
    //
    private String mobilenumber,password;
    private String from;

    private ScreenNotification screenNotification;
    private String param;
    private String emailadd;
    private String emailPassword;

    String status,message;

    UserProfile profile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(SharedPref.getLoginStatus(getApplicationContext()).equals("true")){
            startActivity(new Intent(this,MainActivity.class));
        }else{
            setContentView(R.layout.activity_login);
            init();

            et_mobileNumber.setText("9502813588");
            et_mobilePassword.setText("software1");

            et_mobileNumber.clearFocus();
            et_mobilePassword.clearFocus();
        }
    }

    private void init() {
        et_mobileNumber = findViewById(R.id.login_mobile_number);
        btn_email_signIn = findViewById(R.id.login_emailAdd_btn);
        et_login_email = findViewById(R.id.et_login_email);
        et_mobilePassword = findViewById(R.id.login_mobile_password);
        btn_signIn = findViewById(R.id.login_mobile_signin_btn);
        login_tv_useEmailAdd = findViewById(R.id.login_tv_useEmailAdd);
        login_tv_forgotPassword = findViewById(R.id.login_tv_forgotPassword);
        login_mobile_layout = findViewById(R.id.login_mobile_layout);
        login_useEmailAdd_layout = findViewById(R.id.login_email_layout);
        et_email_password = findViewById(R.id.login_email_password);
        login_tv_Register = findViewById(R.id.login_tv_Register);
        screenNotification = new ScreenNotification();

        listener();
    }

    private void listener() {
        btn_signIn.setOnClickListener(this);
        login_tv_forgotPassword.setOnClickListener(this);
        login_tv_useEmailAdd.setOnClickListener(this);
        btn_email_signIn.setOnClickListener(this);
        login_tv_Register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_mobile_signin_btn:
                validateInputs();
                break;
            case R.id.login_emailAdd_btn:
                validateEmail();
                break;
            case R.id.login_tv_useEmailAdd:
                login_useEmailAdd_layout.setVisibility(View.VISIBLE);
                login_mobile_layout.setVisibility(View.GONE);
                break;
            case R.id.login_tv_Register:
                Intent intent = new Intent(this, RegisterDriverActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void validateInputs() {

        if(et_mobilePassword.getText().toString().isEmpty() || et_mobileNumber.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"All fields are mandatory.",Toast.LENGTH_SHORT).show();
        }else{
            from  = et_mobileNumber.getText().toString();
            password = et_mobilePassword.getText().toString();
            validateCredentials();
            try {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                // TODO: handle exception
            }
//            showCustomDialog();
        }

    }
    private void validateEmail() {

        if(et_login_email.getText().toString().isEmpty() || et_email_password.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"All fields are mandatory.",Toast.LENGTH_SHORT).show();
        }else if(!CommonFunctions.isValidEmail(et_login_email.getText().toString())){
            et_login_email.setError("Email is invalid.");
        }
        else{
            from  = et_login_email.getText().toString();
            password = et_email_password.getText().toString();
            try {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                // TODO: handle exception
            }

            validateCredentials();
            showCustomDialog();
        }

    }
    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.turnon_notifcations_layout, viewGroup, false);
        //Now we need an AlertDialog.Builder object

        Button confirm = dialogView.findViewById(R.id.turnOn_notifications_yes_btn);
        Button skip = dialogView.findViewById(R.id.turnOn_notifications_skip_btn);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });

    }
    private void validateCredentials() {
        if (CommonFunctions.getConnectivityStatus(LoginActivity.this) > 0) {
            screenNotification.progressDialog(LoginActivity.this, "Please Wait", "Signing in...");

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("from",from);
                jsonObject.put("password",password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("okhttp","FROM DATA: "+ from);
            Log.d("okhttp","password DATA: "+ password);
            Log.d("okhttp","PARAMs DATA: "+ jsonObject.toString());

            //Validate Entry
            try {

                CommonAPI api = RetrofitBuild.getClient().create(CommonAPI.class);
                Call<CommonResponse> responseCall = api.login(from,password);
                responseCall.enqueue(new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                        screenNotification.hideProgressDialog(LoginActivity.this);

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
                                //LIST OF OBJECT
                                List<UserProfile> userProfiles = new Gson().fromJson(json, new TypeToken<List<UserProfile>>() {
                                }.getType());
                                //OBJECT
                                for (UserProfile schoolUserProfile : userProfiles) {
                                    profile = schoolUserProfile;
                                    Log.d("okhttp",profile.getMobileNumber());
                                }

                                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                                        //set icon
                                        .setIcon(R.drawable.ic_local_airport_light_blue_400_48dp)
                                        //set title
                                        .setTitle("UXplore")
                                        .setCancelable(false)
                                        //set message
                                        .setMessage("Login Successful")
                                        //set positive button
                                        .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //set what would happen when positive button is clicked
                                                dialogInterface.dismiss();

                                                SharedPref.saveLoginStatus(getApplicationContext(),"true");
                                                SharedPref.saveEmailAdd(getApplicationContext(),profile.getEmailAddress());
                                                SharedPref.saveID(getApplicationContext(),profile.getID());
                                                SharedPref.saveUserID(getApplicationContext(),profile.getUserID());
                                                SharedPref.saveUserType(getApplicationContext(),profile.getUserTypeID());
                                                SharedPref.saveMobileNumber(getApplicationContext(),profile.getMobileNumber());

                                                startActivity(new Intent(LoginActivity.this,MainActivity.class));

                                            }
                                        })
                                        .show();
                                break;

                            default:
                                AlertDialog alertDialog1 = new AlertDialog.Builder(LoginActivity.this)
                                        //set icon
                                        .setIcon(R.drawable.ic_error_red_900_36dp)
                                        //set title
                                        .setTitle("Login Failed")
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
                    public void onFailure(Call<CommonResponse> call, Throwable t) {
                        screenNotification.hideProgressDialog(LoginActivity.this);
                        Log.d("okhttp","ERROR : "+t.getMessage());



                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            screenNotification.hideProgressDialog(LoginActivity.this);
            screenNotification.showToast(this,"No Internet Connection.");
        }
    }


    /*private final Callback<CommonResponse> getAuthenticateUserCallBackV2 = new Callback<CommonResponse>() {
        @Override
        public void onResponse(@NonNull Call<CommonResponse> call, Response<CommonResponse> response) {

            ResponseBody errorBody = response.errorBody();

            //Decrypt message from API
            String decryptedMessage = null;
            if (response.body() != null)
                decryptedMessage = response.body().getMessage();


            Log.d("okhttp", "LoginActivity: - Message " +decryptedMessage);
            Log.d("okhttp", "LoginActivity: - BODY " +response.body());
            Log.d("okhttp", "LoginActivity: - RAW " +response.raw().body());


            if (errorBody == null) {
                switch (response.body().getStatus()) {
                    case "000":

                        String decryptedData =  response.body().getData();
                        Log.d("okhttp", "LoginActivity: - Data " +decryptedData);

                        Log.d("okhttp", "LoginActivity: - Data " +decryptedData);

                        screenNotification.hideProgressDialog(LoginActivity.this);

                        if (decryptedData.equals("error") || decryptedMessage.equals("error")) {
                            screenNotification.showToast(LoginActivity.this,"Something went wrong,Please try again!");
                        } else {
                            et_mobileNumber.setText("");
                            et_mobilePassword.setText("");

                            et_email_password.setText("");
                            et_login_email.setText("");

//                            //try to get the session id and profile
//                            String strprofile = CommonFunctions.parseJSON(decryptedData, "profile");
//
//                            //LIST OF OBJECT
//                            List<PlaceName> merchantUserProfileList = new Gson().fromJson(strprofile, new TypeToken<List<PlaceName>>() {
//                            }.getType());
//                            //OBJECT
//                            PlaceName profile = null;
//                            for (PlaceName sponsorUserProfile : merchantUserProfileList) {
//                                profile = sponsorUserProfile;
//                            }
//                            if (profile != null) {
//                                et_mobileNumber.setText("");
//                                et_mobilePassword.setText("");
//
//                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                            }
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));

                        }
                        break;
                    default:
                        screenNotification.hideProgressDialog(LoginActivity.this);
                        if (decryptedMessage.equals("error")) {
                            Log.d("okhttp", "MainActivity: " +decryptedMessage);
                        } else {
                            Log.d("okhttp", "MainActivity: " +decryptedMessage);
                            new ScreenNotification().showToast(getApplicationContext(),decryptedMessage);
                        }
                        break;
                }
            } else {
                screenNotification.hideProgressDialog(LoginActivity.this);
            }
        }

        @Override
        public void onFailure(Call<CommonResponse> call, Throwable t) {
            screenNotification.hideProgressDialog(LoginActivity.this);
            Log.d("okhttp", "MainActivity: " + t.getMessage());
        }
    };*/

    @Override
    public void onBackPressed() {
       if(login_useEmailAdd_layout.isShown()){
           login_useEmailAdd_layout.setVisibility(View.GONE);
           login_mobile_layout.setVisibility(View.VISIBLE);
       }else{
           super.onBackPressed();
       }
    }


}
