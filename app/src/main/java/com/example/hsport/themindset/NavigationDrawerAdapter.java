/*
package com.example.hsport.themindset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hsport.themindset.NavigationDrawerItem;

import java.util.List;

*/
/**
 * Created by Lambros on 09/03/16.
 *//*

public class NavigationDrawerAdapter extends BaseAdapter {

    private List<NavigationDrawerItem> mDrawerItems;
    private LayoutInflater mLayoutInflater;


    public NavigationDrawerAdapter(Context context,List<NavigationDrawerItem> mDrawerItems) {
        super();
        this.mDrawerItems = mDrawerItems;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        //Not used in this app
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mLayoutInflater.inflate(R.layout.custom_navigation_drawer, null);
        NavigationDrawerItem navigationDrawerItem = mDrawerItems.get(position);

        TextView title = (TextView) convertView.findViewById(R.id.navigation_item_title);
        title.setText(navigationDrawerItem.getTitle());

        ImageView icon = (ImageView) convertView.findViewById(R.id.navigation_item_icon);
        icon.setImageResource(navigationDrawerItem.getIconId());

        return convertView;
    }
}

*/
