package com.iskhak.servicehelper.injection.component;

import android.app.Application;
import android.content.Context;

import com.iskhak.servicehelper.data.DataManager;
import com.iskhak.servicehelper.data.SyncService;
import com.iskhak.servicehelper.injection.ApplicationContext;
import com.iskhak.servicehelper.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext
    Context context();
    Application application();
    DataManager dataManager();
}
