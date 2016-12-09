package com.iskhak.servicehelper.injection.module;

import android.app.Application;
import android.content.Context;

import com.iskhak.servicehelper.data.remote.ConnectionService;
import com.iskhak.servicehelper.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application){
        mApplication = application;
    }

    @Provides
    Application provideApplication(){
        return  mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return mApplication;
    }

    @Provides
    @Singleton
    ConnectionService proviceConnectionService(){
        return ConnectionService.Creator.newConnectionService();
    }
}
