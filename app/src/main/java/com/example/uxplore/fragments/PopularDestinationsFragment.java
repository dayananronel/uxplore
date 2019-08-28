package com.example.uxplore.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uxplore.R;
import com.example.uxplore.adapters.GenericRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularDestinationsFragment extends Fragment {

    //variables
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    View view;
    private ArrayList<String> mSpotID = new ArrayList<>();

    public PopularDestinationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_popular_destinations, container, false);

        initImageBitmaps();

        return view;
    }

    private void initImageBitmaps(){
        mImageUrls.add("https://www.1zoom.me/big2/21/326668-svetik.jpg");
        mNames.add("Colon");
        mImageUrls.add("https://s1.1zoom.me/big0/959/327883-svetik.jpg");
        mNames.add("Lahug");
        mImageUrls.add("https://www.1zoom.me/prev2/328/327373.jpg");
        mNames.add("Labangon");
        mImageUrls.add("https://www.1zoom.me/prev2/328/327789.jpg");
        mNames.add("Talamban");
        mImageUrls.add("https://www.1zoom.me/prev2/328/327534.jpg");
        mNames.add("Lapu-Lapu");
        mImageUrls.add("https://www.1zoom.me/prev2/328/327678.jpg");
        mNames.add("Mactan");

        initRecyclerView();

    }

    private void initRecyclerView(){
        RecyclerView recyclerView =view.findViewById(R.id.destinations_popular_recyclerView);
        GenericRecyclerViewAdapter genericRecyclerViewAdapter = new GenericRecyclerViewAdapter(getContext(),mNames,mImageUrls,mSpotID);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(genericRecyclerViewAdapter);
    }
}
