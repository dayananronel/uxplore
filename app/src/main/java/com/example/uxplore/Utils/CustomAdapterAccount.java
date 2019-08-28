package com.example.uxplore.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uxplore.R;
import com.example.uxplore.model.AccountItemObject;

import java.util.List;

public class CustomAdapterAccount extends BaseAdapter {

    private LayoutInflater lInflater;
    private List<AccountItemObject> listStorage;


    public CustomAdapterAccount(Context context, List<AccountItemObject> customizedListView) {

        lInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;

    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder listViewHolder;

        if(convertView == null){

            listViewHolder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.listview_account, parent, false);
            listViewHolder.textInListView = convertView.findViewById(R.id.account_category);
            listViewHolder.imageInListView = convertView.findViewById(R.id.account_category_image);
            convertView.setTag(listViewHolder);

        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }

        listViewHolder.textInListView.setText(listStorage.get(position).getTitle());
        listViewHolder.imageInListView.setImageResource(listStorage.get(position).getImageId());
        return convertView;

    }

    static class ViewHolder{

        TextView textInListView;
        ImageView imageInListView;

    }
    }

