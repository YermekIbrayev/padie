package com.iskhak.servicehelper.data.model;

import android.os.Build;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.sql.Timestamp;

@AutoValue
public abstract class SelectedItemsAddExtra {
    public abstract Integer pid();
    public abstract Integer additionalQID();

    public static Builder builder(){
        return new AutoValue_SelectedItemsAddExtra.Builder();
    }

    public static TypeAdapter<SelectedItemsAddExtra> typeAdapter(Gson gson){
        return new AutoValue_SelectedItemsAddExtra.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public static abstract class Builder{
        public abstract Builder setPid(Integer id);
        public abstract Builder setAdditionalQID(Integer additionalQID);
        public abstract SelectedItemsAddExtra build();
    }
/*
    private int id;
    private int pid;
    private Timestamp created;
    private int additionalQID;
*/
}
