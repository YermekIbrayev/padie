package com.iskhak.servicehelper.ui;

import com.iskhak.servicehelper.data.model.dbServiceItem;

import java.util.ArrayList;
import java.util.HashSet;

public class SelectedItemsList {
    private HashSet<Integer> servicesID = new HashSet<>();
    private ArrayList<dbServiceItem> mainSelections = new ArrayList<>();
    private ArrayList<dbServiceItem> extraSelections = new ArrayList<>();

    public void addServiceName(int serviceID){
        servicesID.add(serviceID);
    }

    public void addMainSelection(int mainSelectionID, int mainSelectionPID){
        dbServiceItem newItem = dbServiceItem.builder()
                .setId(mainSelectionID)
                .setPid(mainSelectionPID)
                .setName("")
                .build();
                //new dbServiceItem(mainSelectionID, mainSelectionPID, "");
        mainSelections.add(newItem);
    }

    public void addExtraSelection(int extraSelectionID, int extraSelectionPID){
        dbServiceItem newItem = dbServiceItem.builder()
                .setId(extraSelectionID)
                .setPid(extraSelectionPID)
                .setName("")
                .build();
                //new dbServiceItem(extraSelectionID, extraSelectionPID, "");
        extraSelections.add(newItem );
    }

    public HashSet<Integer> getServicesID() {
        return servicesID;
    }

    public ArrayList<dbServiceItem> getMainSelections() {
        return mainSelections;
    }

    public ArrayList<dbServiceItem> getExtraSelections() {
        return extraSelections ;
    }

    public void clear(){
        servicesID.clear();
        mainSelections.clear();
        extraSelections.clear();
    }
}
