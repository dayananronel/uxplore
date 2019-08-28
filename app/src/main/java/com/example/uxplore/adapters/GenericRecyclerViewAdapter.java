package com.example.uxplore.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.uxplore.R;
import com.example.uxplore.activities.DetailsActivity;

import java.util.ArrayList;

public class GenericRecyclerViewAdapter extends RecyclerView.Adapter<GenericRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mSpotID = new ArrayList<>();

    private Context mContext;

    public GenericRecyclerViewAdapter(Context context, ArrayList<String> imageNames, ArrayList<String> images,ArrayList<String> mpackageID){
        mImageNames = imageNames;
        mImages = images;
        mContext = context;
        mSpotID = mpackageID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.explore_layout_list_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        Glide.with(mContext)
                .load(mImages.get(i))
                .placeholder(R.drawable.landingimage)
                .error(R.drawable.placeholder)
                .thumbnail(1)
                .into(viewHolder.imageView);

        viewHolder.name.setText(mImageNames.get(i));
        viewHolder.spotid.setText(mSpotID.get(i));
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mImageNames.get(i),Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, DetailsActivity.class)
                        .putExtra("packagename",mImageNames.get(i))
                        .putExtra("spotid",mSpotID.get(i)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name, spotid;
        RelativeLayout parentLayout;

        ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_explore);
            name = itemView.findViewById(R.id.tv_explore_name);
            spotid = itemView.findViewById(R.id.tv_explore_spotid);
            parentLayout = itemView.findViewById(R.id.explore_listItem_parent);
        }
    }
}
