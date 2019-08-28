package com.example.uxplore.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.example.uxplore.Utils.CommonFunctions;
import com.example.uxplore.Utils.RetrofitBuild;
import com.example.uxplore.Utils.SharedPref;
import com.example.uxplore.api.CommonAPI;
import com.example.uxplore.response.GenericResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.uxplore.fragments.AccountFragment;
import com.example.uxplore.fragments.BookingsFragment;
import com.example.uxplore.fragments.HomeFragment;
import com.example.uxplore.R;
import com.example.uxplore.fragments.PackagesFragment;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction ft ;

    BottomNavigationView bottomNavigationView;
    private int count = 0;

    ProgressDialog screenNotification;
    private String status;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bottomNavigationView = findViewById(R.id.navBot);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.menu_explore:
                        startActivity(new Intent(MainActivity.this,ExploreActivity.class));
                        break;
                    case R.id.menu_account:
                        // Begin the transaction
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayout, new AccountFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case R.id.menu_packages:
                         ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayout, new PackagesFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case R.id.menu_bookings:
                        ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frameLayout, new BookingsFragment());
                        ft.addToBackStack("BookingsFragment");
                        ft.commit();

                        break;

                }
                return true;
            }
        });

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.add(R.id.frameLayout, new HomeFragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Press back again to logout.", Toast.LENGTH_SHORT).show();
        if(count == 1){
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.dismiss();

                            screenNotification = new ProgressDialog(MainActivity.this);
                            screenNotification.setMessage("Logging out. Please wait...");
                            screenNotification.setCancelable(false);
                            screenNotification.show();

                            logout();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }else{
            count+=1;
        }
    }

    private void logout() {
        if(CommonFunctions.getConnectivityStatus(getApplicationContext())>0){

            //Validate Entry
            try {

                CommonAPI api = RetrofitBuild.getClient().create(CommonAPI.class);
                Call<GenericResponse> responseCall = api.logout(SharedPref.getID(getApplicationContext()));
                responseCall.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

                        screenNotification.dismiss();

                        status = String.valueOf(Objects.requireNonNull(response.body()).getStatus());
                        message = String.valueOf(response.body().getMessage());


                        Log.d("okhttp","STAT: "+status);
                        Log.d("okhttp","Message: "+message);
                        Log.d("okhttp","DATA: "+ response.body().getData());

                        Log.d("okhttp","RESPONSE BODY: "+ response.toString());

                        switch (status){
                            case "000":

                                SharedPref.clearPreferences(getApplicationContext());

                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                                break;

                            default:

                                new AlertDialog.Builder(MainActivity.this)
                                        //set icon
                                        .setIcon(R.drawable.ic_error_red_900_36dp)
                                        //set title
                                        .setTitle("Logout Failed")
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
                        screenNotification.hide();
                        Log.d("okhttp","ERROR : "+t.getMessage());
                    }
                });


            } catch (Exception e) {
                screenNotification.dismiss();
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getApplicationContext(),"No Internet Connection.",Toast.LENGTH_SHORT).show();
        }
    }
}
