package com.iskhak.serviceprovider.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.helpers.DataHolder;

import java.util.ArrayList;
import java.util.List;

public class RequestAdapter extends ArrayAdapter{

    List<PackageModel> orders;
    Context context;
    View row;

    public RequestAdapter(Context context, int resource, List<PackageModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.orders =objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;
        RequestHolder holder;
        if(row==null){
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = vi.inflate(R.layout.job_item, null);
            holder = new RequestHolder(row);
            row.setTag(holder);
        } else {
            holder = (RequestHolder) convertView.getTag();
        }
        holder.setViewsText(orders.get(position));

        return row;
    }

    public void add(List<PackageModel> orders){
        this.orders = orders;
        notifyDataSetChanged();
    }

    public int getSize(){
        return orders.size();
    }
    public void clear(){
        orders.clear();
        notifyDataSetChanged();
    }
}
