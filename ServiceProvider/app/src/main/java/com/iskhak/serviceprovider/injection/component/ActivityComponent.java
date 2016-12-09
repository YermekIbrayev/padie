package com.iskhak.serviceprovider.injection.component;

import android.app.Fragment;

import com.iskhak.serviceprovider.injection.PerActivity;
import com.iskhak.serviceprovider.injection.module.ActivityModule;
import com.iskhak.serviceprovider.ui.RequestsTab;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(RequestsTab requestsTab);

}
