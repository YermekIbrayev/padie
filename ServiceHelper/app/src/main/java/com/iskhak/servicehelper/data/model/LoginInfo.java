package com.iskhak.servicehelper.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class LoginInfo {
    @Nullable
    public abstract String username();
    public abstract String email();
    @Nullable
    public abstract String firstname();
    @Nullable
    public abstract String lastname();
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
        public abstract Builder setEmail(String email);
        public abstract Builder setFirstname(String firstname);
        public abstract Builder setLastname(String lastname);
        public abstract Builder setPassword(String password);
        public abstract LoginInfo build();
    }
}
