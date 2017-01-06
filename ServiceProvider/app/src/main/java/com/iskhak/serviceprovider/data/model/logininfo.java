package com.iskhak.serviceprovider.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class LoginInfo {
    public abstract String username();
    public abstract String password();

    public static Builder builder(){
        return new AutoValue_LoginInfo.Builder();
    }

    public static TypeAdapter<LoginInfo> typeAdapter(Gson gson){
        return new AutoValue_LoginInfo.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setUsername(String username);
        public abstract Builder setPassword(String password);
        public abstract LoginInfo build();
    }
}
