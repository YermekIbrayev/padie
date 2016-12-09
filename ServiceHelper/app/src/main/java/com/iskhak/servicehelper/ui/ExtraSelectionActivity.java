package com.iskhak.servicehelper.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.iskhak.servicehelper.data.model.SelectedItemsAdd;
import com.iskhak.servicehelper.data.model.SelectedItemsAddExtra;
import com.iskhak.servicehelper.data.model.dbServiceItem;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.data.model.ServiceItem;

import java.util.ArrayList;
import java.util.List;

public class ExtraSelectionActivity extends SelectionActivity {

    private SelectionActivity.State currentState = State.EXTRA;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.activity_extra_selection_actitvity, currentState);
        context = this;
    }

    @Override
    protected String getCaptionText() {
        return DataHolder.getInstance().getExtraQuestionText();
    }

    @Override
    public List<ServiceItem> getItems() {
        return DataHolder.getInstance().getExtraSelectionNames();
    }

    @Override
    public void addSelectionID(int id, int pid) {
        SelectedItemsAddExtra item = SelectedItemsAddExtra.builder()
                .setPid(pid)
                .setAdditionalQID(id)
                .build();
        DataHolder.getInstance().addSelectedItemsAddExtra(item);
        //DataHolder.getInstance().addExtraSelectionID(id, pid);
    }

    @Override
    public void onNextButton(View view) {
        Intent intent = new Intent(context, CleaningAddressActivity.class);
        startActivity(intent);
    }

}
