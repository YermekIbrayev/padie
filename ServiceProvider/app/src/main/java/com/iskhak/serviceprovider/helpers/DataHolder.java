package com.iskhak.serviceprovider.helpers;

import android.content.Context;

import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.data.model.TokenModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataHolder {

    private TokenModel token;

    private UserPreferences userPreferences;

    private boolean isRunning;

    public static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance(){
        return holder;
    }

    public DataHolder(){
    }

    public void setContext(Context context){
        userPreferences = new UserPreferences(context);
    }

    public UserPreferences getUserPreferences(){
        return userPreferences;
    }

    public TokenModel getToken(){
        return token;
    }
    public void setToken(TokenModel token){
        this.token = token;
    }

    public boolean isRunning(){
        return isRunning;
    }

    public void stopActivity(){
        isRunning = false;
    }

    public void startActivity(){
        isRunning = true;
    }
}
