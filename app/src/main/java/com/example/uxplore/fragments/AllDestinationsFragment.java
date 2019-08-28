package com.example.uxplore.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uxplore.R;
import com.example.uxplore.adapters.AllDestinationsChildRecyclerViewAdapter;
import com.example.uxplore.adapters.AllDestinationsRecyclerViewAdapter;
import com.example.uxplore.adapters.PackagesRecyclerViewAdapter;
import com.example.uxplore.model.PlaceName;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllDestinationsFragment extends Fragment {

    View view;
    private ArrayList<String> mPlace = new ArrayList<>();
    private ArrayList<PlaceName> mPlaceName = new ArrayList<>();

    public AllDestinationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_destinations, container, false);

        initImageBitmaps();

        return view;
    }
    private void initImageBitmaps(){

        mPlace.add("Danao Bohol");
        mPlaceName.add(new PlaceName("Danao Bohol","Sea of Clouds"));

        mPlace.add("Danao Bohol");



        mPlaceName.add(new PlaceName("Danao Bohol","Sea of Clouds"));
        mPlaceName.add(new PlaceName("Danao Bohol","Sea of Clouds"));
        mPlaceName.add(new PlaceName("Danao Bohol","Sea of Clouds"));
        mPlaceName.add(new PlaceName("Danao Bohol","Sea of Clouds"));

        mPlace.add("Danao Bohol");
        mPlaceName.add(new PlaceName("Danao Bohol","Sea of Clouds"));

        mPlace.add("Danao Bohol");
        mPlaceName.add(new PlaceName("Danao Bohol","Sea of Clouds"));

        initRecyclerView();

    }

    private void initRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.destinations_all_recyclerview);
        AllDestinationsRecyclerViewAdapter allDestinationsRecyclerViewAdapter = new AllDestinationsRecyclerViewAdapter(getContext(),mPlace,mPlaceName);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(allDestinationsRecyclerViewAdapter);


    }

}
