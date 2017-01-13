package com.iskhak.servicehelper.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class UserInfo {
    public abstract String username();

    public static Builder builder(){
        return new AutoValue_UserInfo.Builder();
    }

    public static TypeAdapter<UserInfo> typeAdapter(Gson gson){
        return new AutoValue_UserInfo.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public static abstract class Builder{
        public abstract Builder setUsername(String username);
        public abstract UserInfo build();
    }
}
