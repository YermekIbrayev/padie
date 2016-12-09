package com.iskhak.servicehelper.injection.component;

import com.iskhak.servicehelper.injection.ConfigPersistent;
import com.iskhak.servicehelper.injection.module.ActivityModule;

import dagger.Component;

@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {
    ActivityComponent activityComponent(ActivityModule activityModule);
}
