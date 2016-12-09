package com.iskhak.serviceprovider.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.Date;

@AutoValue
public abstract class ResponseOrder {
    public abstract Integer id();
    public abstract Integer selectedPkg();
    public abstract String deviceId();
    public abstract Date viewed();

    public static Builder builder(){
        return new AutoValue_ResponseOrder.Builder();
    }

    public static TypeAdapter<ResponseOrder> typeAdapter (Gson gson){
        return new AutoValue_ResponseOrder.GsonTypeAdapter(gson);
    }


    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setId(Integer id);
        public abstract Builder setSelectedPkg(Integer packageId);
        public abstract Builder setDeviceId(String deviceId);
        public abstract Builder setViewed(Date updated);
        public abstract ResponseOrder build();
    }
}
