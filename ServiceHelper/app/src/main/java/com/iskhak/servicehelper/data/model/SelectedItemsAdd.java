package com.iskhak.servicehelper.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.sql.Timestamp;

@AutoValue
public abstract class SelectedItemsAdd {

    public abstract Integer pid();
    public abstract Integer additionalQID();

    public static Builder builder(){
        return new AutoValue_SelectedItemsAdd.Builder();
    }

    public static TypeAdapter<SelectedItemsAdd> typeAdapter(Gson gson){
        return new AutoValue_SelectedItemsAdd.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setPid(Integer pid);
        public abstract Builder setAdditionalQID(Integer additionalQID);
        public abstract SelectedItemsAdd build();
    }
}
