package com.example.uxplore.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uxplore.R;

import java.util.ArrayList;

public class TourHistoryRecyclerViewAdapter extends RecyclerView.Adapter<TourHistoryRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mStatus = new ArrayList<>();
    private ArrayList<String> mDate = new ArrayList<>();
    private ArrayList<String> mLocationPre = new ArrayList<>();
    private ArrayList<String> mLocationPost = new ArrayList<>();
    private Context mContext;

    public TourHistoryRecyclerViewAdapter(Context mContext,ArrayList<String> mStatus, ArrayList<String> mDate, ArrayList<String> mLocationPre, ArrayList<String> mLocationPost) {
        this.mStatus = mStatus;
        this.mDate = mDate;
        this.mLocationPre = mLocationPre;
        this.mLocationPost = mLocationPost;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_history_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.status.setText(mStatus.get(i));
        viewHolder.date.setText(mDate.get(i));
        viewHolder.locationpre.setText(mLocationPre.get(i));
        viewHolder.locationpost.setText(mLocationPost.get(i));


        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mStatus.get(i),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStatus.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView status,date,locationpre,locationpost;
        LinearLayout parentLayout;

        ViewHolder(View itemView){
            super(itemView);
            status = itemView.findViewById(R.id.history_status);
            date = itemView.findViewById(R.id.history_date);
            locationpre = itemView.findViewById(R.id.history_locationPre);
            locationpost = itemView.findViewById(R.id.history_locationPost);
            parentLayout = itemView.findViewById(R.id.history_parent_item);
        }
    }
}
