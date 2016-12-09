package com.iskhak.serviceprovider.injection.component;

import android.app.Application;
import android.content.Context;

import com.iskhak.serviceprovider.data.DataManager;
import com.iskhak.serviceprovider.data.SyncService;
import com.iskhak.serviceprovider.data.remote.ConnectionService;
import com.iskhak.serviceprovider.helpers.RxEventBus;
import com.iskhak.serviceprovider.injection.ApplicationContext;
import com.iskhak.serviceprovider.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext
    Context context();
    Application application();
    ConnectionService connectionService();
/*
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
*/
    DataManager dataManager();
    RxEventBus eventBus();

}