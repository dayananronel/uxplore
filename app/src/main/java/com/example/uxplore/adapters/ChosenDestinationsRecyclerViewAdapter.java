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

public class ChosenDestinationsRecyclerViewAdapter extends RecyclerView.Adapter<ChosenDestinationsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mPlace = new ArrayList<>();
    private ArrayList<String> mAddress = new ArrayList<>();
    private Context mContext;

    public ChosenDestinationsRecyclerViewAdapter(Context mContext, ArrayList<String> mPlace, ArrayList<String> mAddress) {
        this.mPlace = mPlace;
        this.mAddress = mAddress;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chosen_destinations_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {


        viewHolder.place.setText(mPlace.get(i));
        viewHolder.address.setText(mAddress.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mPlace.get(i),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlace.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView place,address;
        LinearLayout parentLayout;

        ViewHolder(View itemView){
            super(itemView);
            place = itemView.findViewById(R.id.chosen_destinations_item_placename);
            address = itemView.findViewById(R.id.chosen_destinations_item_address);
            parentLayout = itemView.findViewById(R.id.chosen_destination_parent);
        }
    }
}
