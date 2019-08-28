package com.example.uxplore.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uxplore.R;
import com.example.uxplore.activities.DetailsActivity;
import com.example.uxplore.model.Favorites;

import java.util.ArrayList;
import java.util.List;

public class FavoritesRecyclerViewAdapter extends RecyclerView.Adapter<FavoritesRecyclerViewAdapter.ViewHolder> {

    List<Favorites> favoritesList;

    private Context mContext;

    public FavoritesRecyclerViewAdapter(Context mContext,  List<Favorites> favoritesList) {
      this.favoritesList = favoritesList;
      this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_favorites,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Glide.with(mContext)
                .load(favoritesList.get(i).getImageURL())
                .placeholder(R.drawable.placeholder)
                .override(250,250)
                .error(R.drawable.placeholder)
                .thumbnail(1)
                .into(viewHolder.imageView);

        viewHolder.placename.setText(favoritesList.get(i).getPlacename());
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,favoritesList.get(i).getPlacename(),Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, DetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView placename;
        ImageView imageView;
        LinearLayout parentLayout;

        ViewHolder(View itemView){
            super(itemView);
            placename = itemView.findViewById(R.id.favorites_placename);
            imageView = itemView.findViewById(R.id.image_favorites);
            parentLayout = itemView.findViewById(R.id.fav_parent_item);
        }
    }
}
