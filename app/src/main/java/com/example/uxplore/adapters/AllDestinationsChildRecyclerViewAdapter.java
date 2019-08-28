package com.example.uxplore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uxplore.R;
import com.example.uxplore.model.PlaceName;

import java.util.ArrayList;


public class AllDestinationsChildRecyclerViewAdapter extends RecyclerView.Adapter<AllDestinationsChildRecyclerViewAdapter.ViewHolder> {

    private ArrayList<PlaceName> mPlaceName ;
    private Context mContext;

    public AllDestinationsChildRecyclerViewAdapter(Context mContext,  ArrayList<PlaceName> mPlaceName) {
        this.mPlaceName = mPlaceName;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.destinations_all_list_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.placeName.setText(mPlaceName.get(i).getPlaceName());

        viewHolder.subLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mPlaceName.get(i).getPlaceName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaceName.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView  placeName;
        RelativeLayout subLayout;

        ViewHolder(View itemView){
            super(itemView);
            placeName = itemView.findViewById(R.id.destinations_all_place_name);
            subLayout = itemView.findViewById(R.id.subLayout);
        }
    }

}