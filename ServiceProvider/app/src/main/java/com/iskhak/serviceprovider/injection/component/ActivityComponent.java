package com.iskhak.serviceprovider.injection.component;

import com.iskhak.serviceprovider.injection.PerActivity;
import com.iskhak.serviceprovider.injection.module.ActivityModule;
import com.iskhak.serviceprovider.ui.FullOrderFragment;
import com.iskhak.serviceprovider.ui.JobsTab;
import com.iskhak.serviceprovider.ui.login.LoginActivity;
import com.iskhak.serviceprovider.ui.RequestsTab;
import com.iskhak.serviceprovider.ui.login.RegistrationActivity;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(RegistrationActivity registrationActivity);
    void inject(LoginActivity loginActivity);
    void inject(RequestsTab requestsTab);
    void inject(JobsTab requestsTab);
    void inject(FullOrderFragment orderFragment);

}
