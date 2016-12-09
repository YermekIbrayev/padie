package com.iskhak.serviceprovider;

import android.app.Application;
import android.content.Context;

import com.iskhak.serviceprovider.injection.component.ApplicationComponent;
import com.iskhak.serviceprovider.injection.component.DaggerApplicationComponent;
import com.iskhak.serviceprovider.injection.module.ApplicationModule;

import timber.log.Timber;

public class ServiceProviderApplication extends Application{
    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate(){
        super.onCreate();

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static ServiceProviderApplication get(Context context){
        return (ServiceProviderApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
