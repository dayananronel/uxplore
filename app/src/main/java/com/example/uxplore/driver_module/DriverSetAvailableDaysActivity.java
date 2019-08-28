package com.example.uxplore.driver_module;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.uxplore.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DriverSetAvailableDaysActivity extends AppCompatActivity implements View.OnClickListener {

    List<Calendar> selectedDates ;
    List<String> availableDays;
    Button setDaysBtn;
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_set_available_days);


        calendarView = findViewById(R.id.calendarView);



         setDaysBtn = findViewById(R.id.setDaysBtn);
         setDaysBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setDaysBtn:
                showSelectedDates();
                break;
        }
    }

    private void showSelectedDates() {
        selectedDates = calendarView.getSelectedDates();
        availableDays = new ArrayList<>();
        Calendar calendar ;

        for (int i = 0; i < selectedDates.size(); i++) {
            calendar = selectedDates.get(i);

            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
            String strDate = format.format(calendar.getTime());
            availableDays.add(strDate);
        }

        showDialog();
    }

    private void showDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Selected Available Days")
                .setMessage("Selected Available Days in ARRAY: "+String.valueOf(availableDays))
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DriverSetAvailableDaysActivity.this, DriverMenuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
