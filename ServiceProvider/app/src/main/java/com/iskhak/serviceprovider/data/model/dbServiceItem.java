package com.iskhak.serviceprovider.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class dbServiceItem {

    public abstract Integer id();
    @Nullable
    public abstract Integer pid();
    public abstract String name();

    public static Builder builder(){
        return new AutoValue_dbServiceItem.Builder();
    }

    public static TypeAdapter<dbServiceItem> typeAdapter (Gson gson){
        return new AutoValue_dbServiceItem.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public static abstract class Builder{
        public abstract Builder setId(Integer id);
        public abstract Builder setPid(Integer pid);
        public abstract Builder setName(String name);
        public abstract dbServiceItem build();
    }

}
