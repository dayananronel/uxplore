package com.example.uxplore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uxplore.R;
import com.example.uxplore.Utils.FavoritesHandler;
import com.example.uxplore.Utils.ScreenNotification;
import com.example.uxplore.adapters.FavoritesRecyclerViewAdapter;
import com.example.uxplore.adapters.GenericRecyclerViewAdapter;
import com.example.uxplore.model.Destinations;
import com.example.uxplore.model.Favorites;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements View.OnClickListener {

    //variables
    private ImageView imgBack,deleteFavorites;
    private FavoritesHandler favoritesHandler;
    private TextView pakcageLabel;
    List<Favorites> favoritesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoritesHandler = new FavoritesHandler(FavoritesActivity.this);
        favoritesList = new ArrayList<>();
        pakcageLabel = findViewById(R.id.toolbar_packageName);
        imgBack = findViewById(R.id.img_back);
        deleteFavorites = findViewById(R.id.delete_fav);

        pakcageLabel.setText("Favorites");


        deleteFavorites.setOnClickListener(this);


        imgBack.setOnClickListener(v -> {
            Intent intent = new Intent(FavoritesActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        });

        //OBJECT
       loadDataFromDatabase();

    }

    private void loadDataFromDatabase() {
        //we are here using the DatabaseManager instance to get all employees
        Cursor cursor = favoritesHandler.getAllFavourites();
        if (cursor.moveToFirst()) {
            do {
                favoritesList.add(new Favorites(
                        cursor.getString(0),
                        cursor.getString(1)
                ));
            } while (cursor.moveToNext());
        }
        initRecyclerView();
    }


    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.favorites_recyclerView);
        FavoritesRecyclerViewAdapter favoritesRecyclerViewAdapter = new FavoritesRecyclerViewAdapter(getApplicationContext(),favoritesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(favoritesRecyclerViewAdapter);
        favoritesRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete_fav:
                 if(favoritesHandler.getAllFavourites().getCount() <= 0){
                     Toast.makeText(getApplicationContext(),"Nothing to delete.",Toast.LENGTH_SHORT).show();
                 }else{
                     favoritesHandler.deleteAll();
                     Toast.makeText(getApplicationContext(),"Favorites Deleted.",Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(this,FavoritesActivity.class));
                 }
                break;
        }
    }
}
