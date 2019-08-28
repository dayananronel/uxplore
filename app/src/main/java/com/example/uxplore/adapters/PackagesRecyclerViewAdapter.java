package com.example.uxplore.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.uxplore.Utils.SharedPref;
import com.example.uxplore.activities.PackageDetailsActivity;

import java.util.ArrayList;

public class PackagesRecyclerViewAdapter extends RecyclerView.Adapter<PackagesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mPackageID = new ArrayList<>();
    private Context mContext;

    public PackagesRecyclerViewAdapter(Context context, ArrayList<String> imageNames, ArrayList<String> images,ArrayList<String> mpackageID){
        mImageNames = imageNames;
        mImages = images;
        mContext = context;
        mPackageID = mpackageID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.packages_list_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(mContext)
                .load(mImages.get(i))
                .placeholder(R.drawable.landingimage)
                .into(viewHolder.imageView);

        viewHolder.name.setText(mImageNames.get(i));
        viewHolder.packageid.setText("ID: "+mPackageID.get(i));
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mImageNames.get(i),Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, PackageDetailsActivity.class)
                        .putExtra("packagename",mImageNames.get(i))
                        .putExtra("spotid",mPackageID.get(i)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,packageid;
        RelativeLayout parentLayout;

        ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_package);
            name = itemView.findViewById(R.id.tv_package_name);
            packageid = itemView.findViewById(R.id.toolbar_packageID);
            parentLayout = itemView.findViewById(R.id.packages_listItem_parent);
            packageid = itemView.findViewById(R.id.tv_package_packageid);
        }
    }
}
