package com.example.uxplore.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.uxplore.R;

import java.util.ArrayList;

public class PackageListRecyclerViewAdapter extends RecyclerView.Adapter<PackageListRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mDescription = new ArrayList<>();
//    private ArrayList<String> mEntranceAdult = new ArrayList<>();
//    private ArrayList<String> mEntranceSenior = new ArrayList<>();
//    private ArrayList<String> mEntranceKids = new ArrayList<>();
//    private ArrayList<String> mSched = new ArrayList<>();
    private ArrayList<String> mSpotFee = new ArrayList<>();

    private Context mContext;


    public PackageListRecyclerViewAdapter(Context mContext,ArrayList<String> mImageNames, ArrayList<String> mImages, ArrayList<String> mDescription,ArrayList<String> mSpotFee) {
        this.mImageNames = mImageNames;
        this.mImages = mImages;
        this.mDescription = mDescription;
        this.mSpotFee = mSpotFee;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.package_details_list_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(mContext)
                .load(mImages.get(i))
                .override(200,200)
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.imageView);

        viewHolder.name.setText(mImageNames.get(i));
        viewHolder.description.setText(mDescription.get(i));
//        viewHolder.entranceFeeAdults.setText(mEntranceAdult.get(i));
//        viewHolder.entranceFeeSenior.setText(mEntranceSenior.get(i));
//        viewHolder.entranceFeeKids.setText(mEntranceKids.get(i));
        viewHolder.package_spotPrice.setText(mSpotFee.get(i));
//        viewHolder.schedule.setText(mSched.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mImageNames.get(i),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,description,entranceFeeAdults,entranceFeeSenior,schedule,entranceFeeKids,package_spotPrice;
        RelativeLayout parentLayout;

        ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_package_details);
            name = itemView.findViewById(R.id.package_name);
            description = itemView.findViewById(R.id.package_description);
//            entranceFeeAdults = itemView.findViewById(R.id.package_entrance_adults);
//            entranceFeeSenior = itemView.findViewById(R.id.package_entrance_seniorcitizen);
//            entranceFeeKids = itemView.findViewById(R.id.package_entrance_kids);
//            schedule = itemView.findViewById(R.id.package_sched);
            package_spotPrice = itemView.findViewById(R.id.package_spotPrice);
            parentLayout = itemView.findViewById(R.id.package_parentLayout);
        }
    }
}
