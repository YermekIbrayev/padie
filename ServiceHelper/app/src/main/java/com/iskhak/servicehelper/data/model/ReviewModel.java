package com.iskhak.servicehelper.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class ReviewModel {
    public abstract Integer pkgId();
    @Nullable
    public abstract UserInfo user();
    //public abstract Integer pid();
    public abstract Float rating();
    public abstract String description();

    public static Builder builder(){
        return new AutoValue_ReviewModel.Builder();
    }

    public static TypeAdapter<ReviewModel> typeAdapter(Gson gson){
        return new AutoValue_ReviewModel.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setPkgId(Integer pkgId);
        public abstract Builder setUser(UserInfo user);
        //public abstract Builder setPid(Integer pid);
        public abstract Builder setRating(Float rating);
        public abstract Builder setDescription(String description);
        public abstract ReviewModel build();
    }
}
