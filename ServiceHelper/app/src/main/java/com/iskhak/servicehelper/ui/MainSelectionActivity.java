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

public class MainSelectionActivity extends SelectionActivity {

    private State currentState = State.SELECTION;

    Context context;

    @Override
    public void addSelectionID(int id, int pid) {

        SelectedItemsAdd item = SelectedItemsAdd.builder()
                .setPid(pid)
                .setAdditionalQID(id)
                .build();
        DataHolder.getInstance().addSelectedItemsAdd(item);
        //DataHolder.getInstance().addMainSelectionID(id, pid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(R.layout.activity_main_selection, currentState);

        context = this;
    }

    @Override
    protected String getCaptionText() {
        return DataHolder.getInstance().getMainQuestionText();
    }

    @Override
    public List<ServiceItem> getItems() {
        return DataHolder.getInstance().getMainSelectionNames();
    }

    @Override
    public void onNextButton(View view) {
        boolean haveExtraQuestions = !DataHolder.getInstance().getExtraSelectionNames().isEmpty();
        Intent intent;
        if(haveExtraQuestions) {
            intent = new Intent(context, ExtraSelectionActivity.class);
        } else {
            intent = new Intent(context, CleaningAddressActivity.class);

        }
        startActivity(intent);
    }


}
