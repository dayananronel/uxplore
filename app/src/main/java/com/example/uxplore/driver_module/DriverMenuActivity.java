package com.example.uxplore.driver_module;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.uxplore.R;
import com.example.uxplore.activities.ChosenDestinationsActivity;
import com.example.uxplore.activities.LoginActivity;
import com.example.uxplore.activities.MainActivity;
import com.example.uxplore.activities.TourHistoryActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class DriverMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView driver_photo,driver_menu_profile_photo;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        driver_photo = findViewById(R.id.driver_menu_image);

        View view = navigationView.getHeaderView(0);
        driver_menu_profile_photo = view.findViewById(R.id.driver_menu_profile_photo);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable()
                .setColor(Color.BLACK);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Glide.with(this)
                .load(R.drawable.ronel)
                .override(250,250)
                .centerCrop()
                .transform(new CircleCrop())
                .into(driver_photo);

        Glide.with(this)
                .load(R.drawable.ronel)
                .override(250,250)
                .centerCrop()
                .transform(new CircleCrop())
                .into(driver_menu_profile_photo);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(getApplicationContext(),"Press back again to logout.", Toast.LENGTH_SHORT).show();
            if(count == 1){
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(DriverMenuActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }else{
                count+=1;
            }
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_notifications) {
            startActivity(new Intent(getApplicationContext(), SendNotificationActivity.class));
        } else if (id == R.id.nav_setCalendar) {
            startActivity(new Intent(getApplicationContext(), DriverSetAvailableDaysActivity.class));
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(getApplicationContext(), TourHistoryDriverActivity.class));
        } else if (id == R.id.nav_reports) {
            Toast.makeText(getApplicationContext(),"Reports",Toast.LENGTH_SHORT).show();
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
