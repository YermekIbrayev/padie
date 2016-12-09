package com.iskhak.servicehelper;

import android.app.Application;
import android.content.Context;

import com.iskhak.servicehelper.injection.component.ApplicationComponent;
import com.iskhak.servicehelper.injection.component.DaggerApplicationComponent;
import com.iskhak.servicehelper.injection.module.ApplicationModule;

import timber.log.Timber;

public class ServiceHelperApplication extends Application{

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate(){
        super.onCreate();

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static ServiceHelperApplication get(Context context){
        return (ServiceHelperApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent(){
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }
}
