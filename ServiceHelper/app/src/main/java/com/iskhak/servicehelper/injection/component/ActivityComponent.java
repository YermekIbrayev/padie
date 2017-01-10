package com.iskhak.servicehelper.injection.component;

import com.iskhak.servicehelper.injection.PerActivity;
import com.iskhak.servicehelper.injection.module.ActivityModule;
import com.iskhak.servicehelper.ui.CleaningAddressActivity;
import com.iskhak.servicehelper.ui.EstimatedOrderActivity;
import com.iskhak.servicehelper.ui.chooseprovider.ChooseProviderActivity;
import com.iskhak.servicehelper.ui.login.LoginActivity;
import com.iskhak.servicehelper.ui.login.RegistrationActivity;

import dagger.Subcomponent;
/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(CleaningAddressActivity cleaningAddressActivity);
    void inject(EstimatedOrderActivity estimatedOrderActivity);
    void inject(LoginActivity loginActivity);
    void inject(RegistrationActivity registrationActivity);
    void inject(ChooseProviderActivity chooseProviderActivity);
}
