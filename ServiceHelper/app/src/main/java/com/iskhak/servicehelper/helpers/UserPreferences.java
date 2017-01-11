package com.iskhak.servicehelper.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {
    private static final String USER_NAME = "USER_NAME";
    private static final String CURRENT_USER_ADDRESS = "CURRENT_USER_ADDRESS";
    private static final String SELECTED_ACCOUNT_PREF = "selected_account_pref";
    private SharedPreferences appPrefs;


    private Context context;

    public UserPreferences(Context context){
        this.context = context;
    }

    private SharedPreferences getAppPref(){
        if(appPrefs==null)
            appPrefs = context.getSharedPreferences(SELECTED_ACCOUNT_PREF, Context.MODE_PRIVATE);;
        return appPrefs;
    }

    public void setUserName(String name){
        appPrefs.edit().putString(USER_NAME,name).commit();
    }

    public String getUserName(){
        return getAppPref().getString(USER_NAME,"");
    }

    public void setCurrentUserAddress(String address){
        appPrefs.edit().putString(CURRENT_USER_ADDRESS, address).commit();
    }

    public String getCurrentUserAddress(){
        return getAppPref().getString(CURRENT_USER_ADDRESS,"");
    }
}
