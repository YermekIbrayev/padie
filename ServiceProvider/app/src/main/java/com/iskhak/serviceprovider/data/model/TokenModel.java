package com.iskhak.serviceprovider.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class TokenModel {
    public abstract String token();

    public static Builder builder(){
        return new AutoValue_TokenModel.Builder();
    }

    public static TypeAdapter<TokenModel> typeAdapter(Gson gson){
        return new AutoValue_TokenModel.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setToken(String token);
        public abstract TokenModel build();
    }
}
