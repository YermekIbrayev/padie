package com.iskhak.servicehelper.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.data.DataManager;
import com.iskhak.servicehelper.data.model.SelectedItems;
import com.iskhak.servicehelper.data.model.ServiceGroup;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.ui.MainSelectionActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class ServiceGroupAdapter extends ArrayAdapter implements View.OnClickListener {

    List<ServiceGroup> serviceGroupsNames;
    Context context;
    @Inject
    DataManager mDataHolder;

    Button classItemButton;

    public ServiceGroupAdapter(Context context, int resource, Map<Integer, ServiceGroup> serviceClassNames) {
        super(context, resource, new ArrayList<>(serviceClassNames.values()));
        this.context = context;
        this.serviceGroupsNames = new ArrayList<>();
        this.serviceGroupsNames.addAll(new ArrayList<>(serviceClassNames.values()));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.service_class_item, null);
            classItemButton = (Button) convertView.findViewById(R.id.class_item_button);
            convertView.setTag(classItemButton);
            classItemButton .setOnClickListener(this);
        } else {
            classItemButton  = (Button) convertView.getTag();
        };


        ServiceGroup serviceGroupItem = serviceGroupsNames.get(position);
        classItemButton.setText(serviceGroupItem.name());
        classItemButton.setTag(serviceGroupItem);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        ServiceGroup serviceGroupItem = (ServiceGroup) button.getTag();
        Intent intent = new Intent(context, MainSelectionActivity.class);
        DataHolder.getInstance().setMainSelectionPID(serviceGroupItem.id());
        SelectedItems item = SelectedItems.builder()
                .setServiceID(serviceGroupItem.id())
                .build();
        DataHolder.getInstance().addSelectedItems(item);
        DataHolder.getInstance().setCurrentServiceName(serviceGroupItem.name());
        DataHolder.getInstance().setMainQuestionText(serviceGroupItem.mainQuestion());
        DataHolder.getInstance().setExtraQuestionText(serviceGroupItem.extraQuestion());
        context.startActivity(intent);

        DataHolder.getInstance().addServiceID(serviceGroupItem.id());
    }
}
