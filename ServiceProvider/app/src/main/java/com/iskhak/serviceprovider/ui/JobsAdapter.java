package com.iskhak.serviceprovider.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.data.model.PackageModel;

import java.util.List;

public class JobsAdapter extends ArrayAdapter {
    List<PackageModel> jobsList;
    Context context;
    View row;

    public JobsAdapter(Context context, int resource, List<PackageModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.jobsList =objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;
        RequestHolder holder = null;
        if(row==null){
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = vi.inflate(R.layout.job_item, null);
            holder = new RequestHolder(row);
            row.setTag(holder);
        } else {
            holder = (RequestHolder) convertView.getTag();
        }
        holder.setViewsText(jobsList.get(position));

        return row;
    }

    public void add(List<PackageModel> orders){
        this.jobsList = orders;
        notifyDataSetChanged();
    }

    @Override
    public void add(Object object) {
        if(!jobsList.contains(object))
            super.add(object);
    }

    public int getSize(){
        return jobsList.size();
    }
    public void clear(){
        jobsList.clear();
        notifyDataSetChanged();
    }
}
