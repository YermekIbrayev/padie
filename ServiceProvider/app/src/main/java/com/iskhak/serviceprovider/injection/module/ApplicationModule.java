package com.iskhak.serviceprovider.injection.module;

import android.app.Application;
import android.content.Context;

import com.iskhak.serviceprovider.data.remote.ConnectionService;
import com.iskhak.serviceprovider.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ConnectionService provideService() {
        return ConnectionService.Creator.newServicesList();
    }

}