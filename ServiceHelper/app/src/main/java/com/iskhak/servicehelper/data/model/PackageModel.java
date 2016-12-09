package com.iskhak.servicehelper.data.model;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.sql.Timestamp;
import java.util.List;

@AutoValue
public abstract class PackageModel {

    @Nullable
    public abstract Integer id();
    @Nullable
    public abstract Integer clientId();
    public abstract Timestamp orderDate();
    public abstract String notes();
    public abstract String address();
    @Nullable public abstract Float price();
    @Nullable public abstract List<SelectedItems> selectedItems();
    @Nullable public abstract List<SelectedItemsAdd> selectedItemsAdd();
    @Nullable public abstract List<SelectedItemsAddExtra> selectedItemsAddExtra();

    public static Builder builder(){
        return new AutoValue_PackageModel.Builder();
    }

    public static TypeAdapter<PackageModel> typeAdapter (Gson gson){
        return new AutoValue_PackageModel.GsonTypeAdapter(gson);
    }


    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setId(Integer id);
        public abstract Builder setClientId(Integer clientId);
        public abstract Builder setOrderDate(Timestamp orderDate);
        public abstract Builder setNotes(String notes);
        public abstract Builder setAddress(String address);
        public abstract Builder setPrice(Float price);
        public abstract Builder setSelectedItems(List<SelectedItems> selectedItems);
        public abstract Builder setSelectedItemsAdd(List<SelectedItemsAdd> selectedItemsAdd);
        public abstract Builder setSelectedItemsAddExtra(List<SelectedItemsAddExtra> selectedItemsAddExtra);
        public abstract PackageModel build();
    }

    /*    private int id;
    private int clientId;
    private Timestamp orderDate;
    private String notes;
    private String address;
    private Timestamp created;
    @Nullable float price;
    private List<SelectedItems> selectedItemsList;
    private List<SelectedItemsAdd> selectedItemsAdd;
    private List<SelectedItemsAddExtra> selectedItemsAddExtra;*/
}