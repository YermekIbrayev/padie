package com.iskhak.servicehelper.extra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.data.model.ServiceItem;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.ui.SelectionActivity;

import java.util.ArrayList;
import java.util.List;

public class ServiceItemAdapter extends ArrayAdapter<ServiceItem> implements OnClickListener {

    private List<ServiceItem> serviceList;
    private Context context;
    private CheckBox name;
    private SelectionActivity selectionActivity;

    public ServiceItemAdapter(Context context, int textViewResourceId, List<ServiceItem> serviceList, SelectionActivity selectionActivity){
        super(context, textViewResourceId, serviceList);
        this.context = context;
        this.serviceList = new ArrayList<>();
        this.serviceList.addAll(serviceList);
        this.selectionActivity = selectionActivity;
    }


    @Override
    public void onClick(View v) {
        CheckBox checkBox = (CheckBox) v;
        ServiceItem serviceItem = (ServiceItem) checkBox.getTag();
        serviceItem.setSelected(checkBox.isChecked());
        selectionActivity.addSelectionID(serviceItem.getID(), DataHolder.getInstance().getMainSelectionPID());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.service_item_activity, null);
            name = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(name);
            name.setOnClickListener(this);
        } else {
            name = (CheckBox) convertView.getTag();
        };

        ServiceItem serviceItem = serviceList.get(position);
        name.setText(serviceItem.getName());
        name.setChecked(serviceItem.isSelected());
        name.setTag(serviceItem);

        return convertView;
    }
}
