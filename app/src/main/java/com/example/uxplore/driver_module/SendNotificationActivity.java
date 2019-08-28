package com.example.uxplore.driver_module;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.uxplore.R;
import com.example.uxplore.activities.MainActivity;
import com.example.uxplore.adapters.ChosenDestinationsRecyclerViewAdapter;
import com.example.uxplore.model.PlaceName;

import java.sql.Driver;
import java.util.ArrayList;

public class SendNotificationActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView photo,close,photo1;
    private ArrayList<String> destinations= new ArrayList<>();
    ScrollView layout_checklist,layout_sendnotification;
    Button send_notification_tourist_btn,driverchecklist_doneBtn;
    Toolbar toolbar;
    boolean clickALl = false;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        init();

        Glide.with(this)
                .load(R.drawable.ronel)
                .override(250,250)
                .centerCrop()
                .transform(new CircleCrop())
                .into(photo);

        initImageBitmaps();
    }

    private void init() {
        photo = findViewById(R.id.send_notification_tourist_img);
        photo1 = findViewById(R.id.driversChecklist_touristimg);
        close  = findViewById(R.id.img_close);
        layout_checklist = findViewById(R.id.layout_driversChecklist);
        layout_sendnotification = findViewById(R.id.layout_sendTouristNotification);
        send_notification_tourist_btn = findViewById(R.id.send_notification_tourist_btn);
        toolbar = findViewById(R.id.toolbar_sendNotifications);
        driverchecklist_doneBtn = findViewById(R.id.driversChecklist_doneBtn);
        listener();

    }
    private void listener(){
        close.setOnClickListener(this);
        send_notification_tourist_btn.setOnClickListener(this);
        driverchecklist_doneBtn.setOnClickListener(this);
    }
    private void initImageBitmaps() {
        destinations.add("Temple of Leah");
        destinations.add("Tops Skyline Cebu");
        destinations.add("Flower Gardens");
        destinations.add("Temple of Leah");
        destinations.add("Temple of Leah");


        initRecyclerView();

    }
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.driversChecklist_recyclerView);
        DriverChecklistAdapter driverChecklistAdapter =
                new DriverChecklistAdapter(getApplicationContext(), destinations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(driverChecklistAdapter);

    }
        @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_close:
                Intent intent = new Intent(this, DriverMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.send_notification_tourist_btn:
                if(layout_sendnotification.isShown()){
                    layout_sendnotification.setVisibility(View.GONE);
                    layout_checklist.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.GONE);

                    Glide.with(this)
                            .load(R.drawable.ronel)
                            .override(250,250)
                            .centerCrop()
                            .transform(new CircleCrop())
                            .into(photo1);

                }
            case  R.id.driversChecklist_doneBtn:
                if(clickALl){
                    Intent intent1 = new Intent(this, DriverMenuActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                }else{
                    Toast.makeText(getApplicationContext(),"Check list not completed yet.",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }


    class DriverChecklistAdapter extends RecyclerView.Adapter<DriverChecklistAdapter.ViewHolder> {

        private ArrayList<String> mDestinations ;
        private Context mContext;

        DriverChecklistAdapter(Context mContext, ArrayList<String> mDestinations) {
            this.mDestinations = mDestinations;
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public DriverChecklistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_checklist,viewGroup,false);
            return new DriverChecklistAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DriverChecklistAdapter.ViewHolder viewHolder, final int i) {
            viewHolder.compatCheckBox.setText(mDestinations.get(i));

            viewHolder.compatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Toast.makeText(SendNotificationActivity.this,buttonView.getText()+" is clicked.",Toast.LENGTH_SHORT).show();

                    if(buttonView.isChecked()){
                      count +=1;

                      if(count == mDestinations.size()){
                          clickALl = true;
                      }
                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            return mDestinations.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            AppCompatCheckBox compatCheckBox;

            ViewHolder(View itemView){
                super(itemView);
                compatCheckBox = itemView.findViewById(R.id.checkBox_driversChecklist);
            }
        }

    }

    @Override
    public void onBackPressed() {
        if(layout_checklist.isShown()){
            layout_sendnotification.setVisibility(View.VISIBLE);
            layout_checklist.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
        }else{
            super.onBackPressed();
        }
    }
}

