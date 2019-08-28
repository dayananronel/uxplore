package com.example.uxplore.fragments;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uxplore.Utils.CommonFunctions;
import com.example.uxplore.Utils.RetrofitBuild;
import com.example.uxplore.Utils.ScreenNotification;
import com.example.uxplore.activities.ExploreActivity;
import com.example.uxplore.adapters.PackagesRecyclerViewAdapter;
import com.example.uxplore.R;
import com.example.uxplore.api.CommonAPI;
import com.example.uxplore.model.Packages;
import com.example.uxplore.response.GenericResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PackagesFragment extends Fragment {

    //variables
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mPackageID = new ArrayList<>();
    private View view;
    private ScreenNotification screenNotification;
    private String status,message;

    public PackagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_packages, container, false);
        screenNotification = new ScreenNotification();

        getPackages();

        return view;
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.package_recylcerView);
        PackagesRecyclerViewAdapter packagesRecyclerViewAdapter = new PackagesRecyclerViewAdapter(getContext(),mNames,mImageUrls,mPackageID);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(packagesRecyclerViewAdapter);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getPackages() {
        if (CommonFunctions.getConnectivityStatus(getActivity()) > 0) {
            screenNotification.progressDialog(getActivity(), "Please Wait", "Getting Packages...");
            //Validate Entry
            try {

                CommonAPI api = RetrofitBuild.getClient().create(CommonAPI.class);
                Call<GenericResponse> responseCall = api.packages(CommonFunctions.getLocalIpAddress());
                responseCall.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        screenNotification.hideProgressDialog(getActivity());

                        status = String.valueOf(response.body().getStatus());
                        message = String.valueOf(response.body().getMessage());


                        Log.d("okhttp","STAT: "+status);
                        Log.d("okhttp","Message: "+message);
                        Log.d("okhttp","DATA: "+ response.body().getData());

                        Log.d("okhttp","RESPONSE BODY: "+ response.toString());

                        switch (status){
                            case "000" :case "200":

                                String json = new Gson().toJson(response.body().getData());
                                Log.d("okhttp","DATAAAA: "+json);



                                //LIST OF OBJECT
                                List<Packages> PackagesList = new Gson().fromJson(json, new TypeToken<List<Packages>>() {
                                }.getType());
                                //OBJECT
                                for (Packages aPackages : PackagesList) {
                                    mImageUrls.add(aPackages.getExtra1());
                                    mNames.add(aPackages.getName());
                                    mPackageID.add(aPackages.getPackageID());

                                }
                                initRecyclerView();
                                break;

                            default:
                                AlertDialog alertDialog1 = new AlertDialog.Builder(getActivity())
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
                        screenNotification.hideProgressDialog(getActivity());
                        Log.d("okhttp","ERROR : "+t.getMessage());

                        AlertDialog alertDialog1 = new AlertDialog.Builder(getContext())
                                //set icon
                                .setIcon(R.drawable.ic_error_red_900_36dp)
                                //set title
                                .setTitle("Initialization Failed")
                                .setCancelable(false)
                                //set message
                                .setMessage("Connection time out. Please try again.")
                                //set positive button
                                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //set what would happen when positive button is clicked
                                        dialogInterface.dismiss();
                                        getPackages();
                                    }
                                })
                                .show();

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            screenNotification.hideProgressDialog(getActivity());
            screenNotification.showToast(getActivity(),"No Internet Connection.");
        }
    }

}
