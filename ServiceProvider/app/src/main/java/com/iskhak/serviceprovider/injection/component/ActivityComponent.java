package com.iskhak.serviceprovider.injection.component;

import com.iskhak.serviceprovider.injection.PerActivity;
import com.iskhak.serviceprovider.injection.module.ActivityModule;
import com.iskhak.serviceprovider.ui.login.LoginActivity;
import com.iskhak.serviceprovider.ui.login.RegistrationActivity;
import com.iskhak.serviceprovider.ui.orders.activity.OrdersActivity;
import com.iskhak.serviceprovider.ui.orders.inprogress.InProgressTab;
import com.iskhak.serviceprovider.ui.orders.newrequests.RequestsTab;
import com.iskhak.serviceprovider.ui.orders.order.OrderFragment;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(RegistrationActivity registrationActivity);
    void inject(LoginActivity loginActivity);
    void inject(OrdersActivity ordersActivity);
    void inject(RequestsTab requestsTab);
    void inject(InProgressTab inProgressTab);
    void inject(OrderFragment orderFragment);

}
