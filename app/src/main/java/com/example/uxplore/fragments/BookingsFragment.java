package com.example.uxplore.fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.uxplore.R;
import com.example.uxplore.Utils.CommonFunctions;
import com.example.uxplore.Utils.DBHandler;
import com.example.uxplore.Utils.RetrofitBuild;
import com.example.uxplore.Utils.ScreenNotification;
import com.example.uxplore.Utils.SharedPref;
import com.example.uxplore.activities.ChosenDestinationsActivity;
import com.example.uxplore.activities.ConfirmationActivity;
import com.example.uxplore.activities.LoginActivity;
import com.example.uxplore.activities.MainActivity;
import com.example.uxplore.api.CommonAPI;
import com.example.uxplore.model.PaymentsCallBack;
import com.example.uxplore.model.UserProfile;
import com.example.uxplore.payment.PaypalConfig;
import com.example.uxplore.response.GenericResponse;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingsFragment extends Fragment implements OnMapReadyCallback,View.OnClickListener, View.OnLongClickListener {

    View view;
    SupportMapFragment mapFragment;
    private GoogleMap map;
    private Context mContext;

    LinearLayout layout_bus,layout_car,layout_van;

    //Views and Instances
    AutocompleteSupportFragment booking_search;
    TextView pickupPoint, dropPoint, estimatedFare, tvPickupPoint, tvDropPoint,tvChosenDestinations,booking_estimatedfare;
    TextView noOfDays;
    Button btn_destinations, btn_proceed, datestart, dateend, arrivaltime,btn_van,btn_bus,btn_car;
    ImageButton imgBtn_van, imgBtn_bus, imgBtn_car;
    AutocompleteSupportFragment autocompleteFragment;
    EditText locationSearch;
    PlacesClient placesClient;
    Address address;
    LatLng latLng, latLng1;
    ArrayList points;
    String t;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String dateType = "";
    private int mHour;
    private int mMinute;
    private int monthStart,monthEnd;
    private int mAMPm;
    String AM_PM ;
    DBHandler dbHandler;

    String payment_type,packageid,paymentid="",paymentamout,userid,driverid="";
    ArrayList<HashMap<String, String>> packages = new ArrayList<HashMap<String, String>>();
    private String cartype = "";
    PayPalConfiguration config;

    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;
    private ScreenNotification screenNotification;
    private String transactionStatus = "";
    private String status;
    private String message;


    public BookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bookings, container, false);

        dbHandler = new DBHandler(getActivity());

        //Paypal Configuration Object
         config = new PayPalConfiguration()
                // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
                // or live (ENVIRONMENT_PRODUCTION)
                .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
                .clientId(PaypalConfig.PAYPAL_CLIENT_ID);
         
         screenNotification = new ScreenNotification();

        Intent intent = new Intent(getContext(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getContext().startService(intent);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);
        mapFragment.getMapAsync(this);

//        // Initialize the SDK
//        Places.initialize(getActivity(), getString(R.string.google_maps_key));
//
//        // Create a new Places client instance
//        placesClient = Places.createClient(getActivity());

        init();
        setListener();


//        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//        autocompleteFragment.setCountry("PH");
//        // Set up a PlaceSelectionListener to handle the response.
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
//                Log.i("ronel", "Place: " + place.getName() + ", " + place.getId());
//
//                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(place.getLatLng(),18);
//                map.addMarker(new MarkerOptions().position(place.getLatLng())
//                        .title(place.getName()).icon(BitmapDescriptorFactory
//                                .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
//                map.animateCamera(cameraUpdate);
//
//            }
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//                Log.i("ronel", "An error occurred: " + status);
//            }
//        });


    }

    private void init() {

//        autocompleteFragment = (AutocompleteSupportFragment)
//                getChildFragmentManager().findFragmentById(R.id.searchBar_booking);
        locationSearch = view.findViewById(R.id.searchBookings);
        tvPickupPoint = view.findViewById(R.id.tv_pickupPoint);
        tvDropPoint = view.findViewById(R.id.tv_dropPoint);
        pickupPoint = view.findViewById(R.id.tv_bookings_pickuppoint);
        dropPoint = view.findViewById(R.id.tv_bookings_droppoint);
        btn_destinations = view.findViewById(R.id.booking_btn_destinations);
        datestart = view.findViewById(R.id.booking_dateStart);
        dateend = view.findViewById(R.id.booking_dateEnd);
        arrivaltime = view.findViewById(R.id.booking_timeArrive);
        imgBtn_van = view.findViewById(R.id.booking_ride_van);
        imgBtn_bus = view.findViewById(R.id.booking_ride_bus);
        imgBtn_car = view.findViewById(R.id.booking_ride_car);
        estimatedFare = view.findViewById(R.id.booking_estimatedfare);
        btn_proceed = view.findViewById(R.id.booking_proceed_btn);
        noOfDays = view.findViewById(R.id.book_noOfdays);
        tvChosenDestinations = view.findViewById(R.id.booking_tvChosenDestinations);
        booking_estimatedfare = view.findViewById(R.id.booking_estimatedfare);

        layout_bus = view.findViewById(R.id.layout_bus);
        layout_van = view.findViewById(R.id.layout_van);
        layout_car = view.findViewById(R.id.layout_car);


        Cursor res = dbHandler.getAllData();
        if(res.getCount() == 0) {
            btn_destinations.setVisibility(View.VISIBLE);
            // show message
            Toast.makeText(getContext(), "Error Nothing found", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        String fee = "",packagename="";

        while (res.moveToNext()) {
            packagename = res.getString(1);
            fee = res.getString(2);
            packageid = res.getString(4);
            buffer.append(res.getString(3)+"\n");
        }
        btn_destinations.setVisibility(View.GONE);
        tvChosenDestinations.setText("Destinations: "+packagename+"\n"+"PackageID: "+packageid+"\n"+"Price: "+fee+"\n"+"Places:  \n"+buffer.toString());
        estimatedFare.setText(fee);

        new SimpleTooltip.Builder(getContext())
                .anchorView(tvChosenDestinations)
                .text("Long press to delete this package.")
                .gravity(Gravity.TOP)
                .animated(true)
                .transparentOverlay(false)
                .build()
                .show();



    }


    private void setListener() {
        btn_proceed.setOnClickListener(this);
        dateend.setOnClickListener(this);
        datestart.setOnClickListener(this);
        arrivaltime.setOnClickListener(this);
        btn_destinations.setOnClickListener(this);
        tvChosenDestinations.setOnLongClickListener(this);

        imgBtn_car.setOnClickListener(this);
        imgBtn_van.setOnClickListener(this);
        imgBtn_bus.setOnClickListener(this);

        locationSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchLocation();
                    return true;
                }
                return false;
            }
        });

        tvDropPoint.setOnClickListener(this);
        tvPickupPoint.setOnClickListener(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.layout_booking_note, viewGroup, false);
        //Now we need an AlertDialog.Builder object

        Button confirm = dialogView.findViewById(R.id.booking_note_confirm);
        ImageView imageView = dialogView.findViewById(R.id.bookingNote_closebutton);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                showPaymentMethod();

                paymentamout = estimatedFare.getText().toString();

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }


    private void showPaymentMethod() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.layout_payments, viewGroup, false);
        //Now we need an AlertDialog.Builder object

        RadioButton cash = dialogView.findViewById(R.id.radio_cash);
        RadioButton paypal = dialogView.findViewById(R.id.radio_paypal);


        cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    payment_type = "CASH";
                }
            }
        });
        paypal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    payment_type = "PAYPAL";
                }
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //setting the view of the builder to our custom view that we already inflated
        builder.setTitle("Payment Method");
        builder.setIcon(R.drawable.ic_payment_black_36dp);
        builder.setView(dialogView);

        builder.setPositiveButton("Pay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                 if(payment_type.equals("PAYPAL")){
                     getPayment();
                 }else{
                     transaction();
                     
                 }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    private void transaction() {
        if (CommonFunctions.getConnectivityStatus(view.getContext()) > 0) {
            screenNotification.progressDialog(getContext(), "Please Wait", "Processing transaction...");


            //get value
            userid = SharedPref.getUserID(getContext());
            
            //Validate Entry
            try {

                CommonAPI api = RetrofitBuild.getClient().create(CommonAPI.class);
                Call<GenericResponse> responseCall = api.transaction(paymentid,paymentamout,packageid,payment_type,userid,transactionStatus,driverid);
                responseCall.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        screenNotification.hideProgressDialog(getContext());

                        status = String.valueOf(response.body().getStatus());
                        message = String.valueOf(response.body().getMessage());


                        Log.d("okhttp","STAT: "+status);
                        Log.d("okhttp","Message: "+message);
                        Log.d("okhttp","DATA: "+ response.body().getData());

                        Log.d("okhttp","RESPONSE BODY: "+ response.toString());

                        switch (status){
                            case "000":

                                String json = new Gson().toJson(response.body().getData());
                                Log.d("okhttp","DATAAAA: "+json);


                                PaymentsCallBack paymentsCallBack;
                                //LIST OF OBJECT
                                List<PaymentsCallBack> paymentsCallBacks = new Gson().fromJson(json, new TypeToken<List<PaymentsCallBack>>() {
                                }.getType());
                                //OBJECT
                                for (PaymentsCallBack schoolUserProfile : paymentsCallBacks) {
                                    paymentsCallBack = schoolUserProfile;
                                    Log.d("okhttp",paymentsCallBack.getTxnno());
                                }

                                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                        //set icon
                                        .setIcon(R.drawable.ic_local_airport_light_blue_400_48dp)
                                        //set title
                                        .setTitle("UXplore")
                                        .setCancelable(false)
                                        //set message
                                        .setMessage("Transaction Successful")
                                        //set positive button
                                        .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //set what would happen when positive button is clicked
                                                dialogInterface.dismiss();
                                                startActivity(new Intent(getContext(), ChosenDestinationsActivity.class).putExtra("packageid",packageid));
                                            }
                                        })
                                        .show();
                                break;

                            default:
                                AlertDialog alertDialog1 = new AlertDialog.Builder(getContext())
                                        //set icon
                                        .setIcon(R.drawable.ic_error_red_900_36dp)
                                        //set title
                                        .setTitle("Initialization Failed")
                                        .setCancelable(false)
                                        //set message
                                        .setMessage(message)
                                        //set positive button
                                        .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //set what would happen when positive button is clicked
                                                dialogInterface.dismiss();
                                            }
                                        })
                                        .show();

                                break;
                        }


                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {
                        screenNotification.hideProgressDialog(getContext());
                        Log.d("okhttp","ERROR : "+t.getMessage());



                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            screenNotification.hideProgressDialog(getContext());
            screenNotification.showToast(getContext(),"No Internet Connection.");
        }
    }

    private void getPayment() {
        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(estimatedFare.getText().toString()), "USD", "Estimated Fee",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(getActivity(), PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

        @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.booking_proceed_btn:

                if(pickupPoint.getText().toString().isEmpty()||
                    dropPoint.getText().toString().isEmpty()||
                    tvChosenDestinations.getText().toString().isEmpty() ||datestart.getText().toString().isEmpty()||
                    dateend.getText().toString().isEmpty() ||arrivaltime.getText().toString().isEmpty()||
                    noOfDays.getText().toString().isEmpty()||
                    cartype.isEmpty() || estimatedFare.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"All fields are mandatory",Toast.LENGTH_SHORT).show();
                }else{
                    showCustomDialog();
                }

                break;

            case R.id.tv_pickupPoint:
                t = "pickup";
                locationSearch.requestFocus();
                locationSearch.setHint("Enter Pickup Point.");
                locationSearch.setHintTextColor(Color.BLUE);
                locationSearch.setTextColor(Color.BLUE);
                break;
            case R.id.tv_dropPoint:
                t = "drop";
                locationSearch.requestFocus();
                locationSearch.setHint("Enter Drop Point.");
                locationSearch.setHintTextColor(Color.BLUE);
                locationSearch.setTextColor(Color.BLUE);
                break;

            case R.id.booking_dateStart:
                dateType = "start";
                showDatePickerDialog();
                break;
            case R.id.booking_dateEnd:
                dateType = "end";
                showDatePickerDialog();
                break;
            case R.id.booking_timeArrive:
                showTimePickerDialog();
                break;

            case R.id.booking_ride_car:
                layout_car.setBackground(getResources().getDrawable(R.drawable.booking_selection_background));
                layout_bus.setBackground(null);
                layout_van.setBackground(null);
                cartype = "car";

                break;
            case R.id.booking_ride_bus:
                layout_car.setBackground(null);
                layout_bus.setBackground(getResources().getDrawable(R.drawable.booking_selection_background));
                layout_van.setBackground(null);
                cartype = "bus";
                break;
            case R.id.booking_ride_van:
                layout_car.setBackground(null);
                layout_bus.setBackground(null);
                layout_van.setBackground(getResources().getDrawable(R.drawable.booking_selection_background));
                cartype = "van";
                break;

            case R.id.booking_btn_destinations:
                getContext().startActivity(new Intent(getContext(), MainActivity.class));
                break;
        }
    }

    private void showDatePickerDialog(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if(dateType.equals("start")){
                            monthStart = view.getDayOfMonth();
                            datestart.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            Log.d("ronel",""+view.getDayOfMonth());
                        }else if(dateType.equals("end")){
                            monthEnd =  view.getDayOfMonth();
                            dateend.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            Log.d("ronel",""+view.getDayOfMonth());


                            if(monthEnd > 0 && monthStart >0){
                                noOfDays.setText(String.valueOf(monthEnd-monthStart));
                            }
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }
    private void showTimePickerDialog(){
        TimePickerDialog timePickerDialog;
        // Launch Time Picker Dialog
         timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        arrivaltime.setText(startTimeFormat().format(calendar.getTime()));


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
    private SimpleDateFormat startTimeFormat(){
        return new SimpleDateFormat("hh:00 a");
    }
    public void searchLocation() {
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;
        List<LatLng> list = null;
        List<Marker> markers = null;
        Marker from = null, to = null;

       if(CommonFunctions.getConnectivityStatus(getContext()) > 0){
           if (location != null || !location.equals("")) {
               Geocoder geocoder = new Geocoder(getActivity());
               try {
                   addressList = geocoder.getFromLocationName(location, 1);
                   address = addressList.get(0);

                   if (t == "drop") {
                       dropPoint.setText(address.getAddressLine(0));
                       latLng1 = new LatLng(address.getLatitude(), address.getLongitude());

                       to = map.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(), address.getLongitude()))
                               .title(address.getPremises()));
                       map.addMarker(new MarkerOptions().position(latLng).title(location));
                       map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                   } else if (t == "pickup") {
                       pickupPoint.setText(address.getAddressLine(0));
                       latLng = new LatLng(address.getLatitude(), address.getLongitude());
                       from = map.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(), address.getLongitude()))
                               .title(address.getPremises()));
                       map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                   }

                   if (pickupPoint.getText().toString().length() > 0 && dropPoint.getText().toString().length() > 0) {

                       map.getUiSettings().setZoomControlsEnabled(true);
                       map.getUiSettings().setMyLocationButtonEnabled(true);
                       map.getUiSettings().setAllGesturesEnabled(true);
                       map.setTrafficEnabled(true);
                       map.animateCamera(CameraUpdateFactory.zoomTo(10));
                       map.addPolyline(new PolylineOptions()
                               .clickable(true)
                               .width(5)
                               .geodesic(true)
                               .add(
                                       latLng,
                                       latLng1));

                       markers.add(from);
                       markers.add(to);

                       LatLngBounds.Builder builder = new LatLngBounds.Builder();
                       for(Marker m : markers) {
                           builder.include(m.getPosition());
                       }

                       int width = getResources().getDisplayMetrics().widthPixels;
                       int height = getResources().getDisplayMetrics().heightPixels;
                       int padding = (int) (height * 0.20); // offset from edges of the map 10% of screen

                       LatLngBounds bounds = builder.build();
                       CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                       map.animateCamera(cu);
                   }


               } catch (Exception e) {
                   Toast.makeText(getContext(), "Location not found", Toast.LENGTH_LONG).show();
                   e.printStackTrace();
               }

           }
       }else{
           Toast.makeText(getContext(),"No internet connection",Toast.LENGTH_SHORT).show();
       }
    }


    @Override
    public boolean onLongClick(View v) {
        showDeletePop(v);
        return false;
    }
    // Display anchored popup menu based on view selected
    private void showDeletePop(View v) {
        PopupMenu popup = new PopupMenu(getContext(), v);
        // Inflate the menu from xml
        popup.inflate(R.menu.context_menu);
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        Toast.makeText(getContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                        dbHandler.deleteAll();
                        tvChosenDestinations.setText("");

                        return true;
                    default:
                        return false;
                }
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(getActivity(), ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", estimatedFare.getText().toString())
                                .putExtra("packageid",packageid));

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }
}
