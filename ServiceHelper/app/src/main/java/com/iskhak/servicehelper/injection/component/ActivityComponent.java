package com.iskhak.servicehelper.injection.component;

import com.iskhak.servicehelper.injection.PerActivity;
import com.iskhak.servicehelper.injection.module.ActivityModule;
import com.iskhak.servicehelper.ui.CleaningAddressActivity;
import com.iskhak.servicehelper.ui.EstimatedOrderActivity;

import dagger.Subcomponent;
/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(CleaningAddressActivity cleaningAddressActivity);
    void inject(EstimatedOrderActivity estimatedOrderActivity);
}
