package com.example.uxplore.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.uxplore.R;

import java.util.ArrayList;

public class StaggeredRecyclerViewAdapter extends  RecyclerView.Adapter<StaggeredRecyclerViewAdapter.ViewHolder>  {

    private static  final String TAG = "StaggeredRecyclerViewAd";

    private ArrayList<String> mPlaces = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    public StaggeredRecyclerViewAdapter( Context mContext,ArrayList<String> mPlaces, ArrayList<String> mImageUrls) {
        this.mPlaces = mPlaces;
        this.mImageUrls = mImageUrls;
        this.mContext = mContext;
    }

    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_grid_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Glide.with(mContext)
                .load(mImageUrls.get(i))
                .placeholder(R.drawable.landingimage)
                .transform(new RoundedCorners(10))
                .into(viewHolder.image);
        viewHolder.place.setText(mPlaces.get(i));
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mPlaces.get(i),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView place;

//        public ViewHolder(@NonNull View itemView, ImageView image, TextView place) {
//            super(itemView);
//            this.image = image;
//            this.place = place;
//        }
        public ViewHolder(View itemView){
            super(itemView);
            this.image = itemView.findViewById(R.id.image_widget);
            this.place = itemView.findViewById(R.id.place_widget);
        }


    }

}
