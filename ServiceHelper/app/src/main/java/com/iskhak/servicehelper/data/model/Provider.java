package com.iskhak.servicehelper.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Provider implements Parcelable {
    public abstract Integer pid();
    public abstract String name();
    public abstract Float rating();
    public abstract Integer reviewCount();

    public static Builder builder(){
        return new AutoValue_Provider.Builder();
    }

    public static TypeAdapter<Provider> typeAdapter(Gson gson){
        return new AutoValue_Provider.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setPid(Integer pid);
        public abstract Builder setName(String name);
        public abstract Builder setRating(Float rating);
        public abstract Builder setReviewCount(Integer reviewCount);
        public abstract Provider build();
    }
}
