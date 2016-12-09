package com.iskhak.servicehelper.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.sql.Timestamp;
import java.util.Date;

@AutoValue
public abstract class SelectedItems {
    public abstract Integer serviceID();

    public static Builder builder(){
        return new AutoValue_SelectedItems.Builder();
    }

    public static TypeAdapter<SelectedItems> typeAdapter(Gson gson){
        return new AutoValue_SelectedItems.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setServiceID(Integer serviceID);
        public abstract SelectedItems build();
    }

/*    private int id;
    private Timestamp created;
    private int serviceID;*/
}
