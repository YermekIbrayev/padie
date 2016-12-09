package com.iskhak.serviceprovider.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

@AutoValue
public abstract class SelectedItems {
    public abstract ServiceGroup serviceID();

    public static Builder builder(){
        return new AutoValue_SelectedItems.Builder();
    }

    public static TypeAdapter<SelectedItems> typeAdapter(Gson gson){
        return new AutoValue_SelectedItems.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setServiceID(ServiceGroup serviceID);
        public abstract SelectedItems build();
    }

/*    private int id;
    private Timestamp created;
    private int serviceID;*/
}
