package com.example.uxplore.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.uxplore.R;
import com.example.uxplore.Utils.CustomAdapterAccount;
import com.example.uxplore.Utils.SharedPref;
import com.example.uxplore.activities.FavoritesActivity;
import com.example.uxplore.activities.TourHistoryActivity;
import com.example.uxplore.model.AccountItemObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private View view;
    private ListView customListView;
    private ImageView imageView;
    private TextView account_name;
    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account, container, false);

        imageView =  view.findViewById(R.id.account_image);
        account_name = view.findViewById(R.id.account_name);

        customListView = view.findViewById(R.id.account_listview);

        final List<AccountItemObject> listViewItems = new ArrayList<AccountItemObject>();

        listViewItems.add(new AccountItemObject("History", R.drawable.ic_history_grey_400_24dp));
        listViewItems.add(new AccountItemObject("Travel Goals", R.drawable.ic_favorite_border_grey_400_24dp));
        listViewItems.add(new AccountItemObject("Notification", R.drawable.ic_notifications_none_grey_400_24dp));
        listViewItems.add(new AccountItemObject("Payment Methods", R.drawable.ic_payment_grey_400_24dp));

        listViewItems.add(new AccountItemObject("Settings", R.drawable.ic_settings_grey_400_24dp));
        listViewItems.add(new AccountItemObject("Reports", R.drawable.ic_report_problem_grey_400_24dp));
        listViewItems.add(new AccountItemObject("Help Center", R.drawable.ic_help_outline_grey_400_24dp));
        listViewItems.add(new AccountItemObject("About", R.drawable.ic_info_outline_grey_400_24dp));

        customListView.setAdapter(new CustomAdapterAccount(view.getContext(), listViewItems));

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        startActivity(new Intent(getContext(), TourHistoryActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getContext(), FavoritesActivity.class));
                        break;
                }
            }
        });


        Glide.with(view.getContext())
                .load(R.drawable.ronel)
                .override(200,200)
                .centerCrop()
                .placeholder(R.drawable.imgdefault)
                .transform(new CircleCrop())
                .into(imageView);

        account_name.setText("Hi!, "+SharedPref.getEmailAdd(getActivity()));

        return view;
    }

}
