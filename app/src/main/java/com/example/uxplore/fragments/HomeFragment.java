package com.example.uxplore.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.uxplore.R;
import com.example.uxplore.adapters.StaggeredRecyclerViewAdapter;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private static final String TAG = "HomeFragment";
    private static final int NUM_COLUMNS = 2;

    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();

    private View view;
    CarouselView carouselView;
   int [] sampleImages = {R.drawable.image_1,R.drawable.image_2,R.drawable.image_3,R.drawable.image_4,R.drawable.image_5,R.drawable.image_6};

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        ScrollView scrollView =view.findViewById(R.id.mainScrollview);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        carouselView = view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        initImageBitmaps();

        return view;
    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        RecyclerView recyclerView = view.findViewById(R.id.homeFragment_recyclerView);
        StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter = new StaggeredRecyclerViewAdapter(getContext(),mNames,mImageUrls);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
    }

}
