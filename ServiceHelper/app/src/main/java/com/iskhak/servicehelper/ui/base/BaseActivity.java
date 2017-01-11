package com.iskhak.servicehelper.ui.base;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.ServiceHelperApplication;
import com.iskhak.servicehelper.injection.component.ActivityComponent;
import com.iskhak.servicehelper.injection.component.ConfigPersistentComponent;
import com.iskhak.servicehelper.injection.component.DaggerConfigPersistentComponent;
import com.iskhak.servicehelper.injection.module.ActivityModule;
import com.iskhak.servicehelper.ui.menu.OrdersActivity;
import com.iskhak.servicehelper.ui.menu.PaymentActivity;
import com.iskhak.servicehelper.ui.menu.ProfileActivity;
import com.iskhak.servicehelper.ui.menu.PromotionsActivity;
import com.iskhak.servicehelper.ui.menu.SettingsActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import timber.log.Timber;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ImageView.OnClickListener{
    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long, ConfigPersistentComponent> sComponentsMap = new HashMap<>();

    private ActivityComponent mActivityComponent;
    private long mActivityId;
    DrawerLayout fullView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (!sComponentsMap.containsKey(mActivityId)) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(ServiceHelperApplication.get(this).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        } else {
            Timber.i("Reusing ConfigPersistentComponent id=%d", mActivityId);
            configPersistentComponent = sComponentsMap.get(mActivityId);
        }
        mActivityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
    }

    @Override
    public void setContentView(int layoutResID){
        fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        LinearLayout activityContainer = (LinearLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID,activityContainer, true);
        super.setContentView(fullView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        toolbar.findViewById(R.id.imageView).setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch (id){
            case R.id.nav_profile:
                intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_payment:
                intent = new Intent(this, PaymentActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_orders:
                intent = new Intent(this, OrdersActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_promotions:
                intent = new Intent(this, PromotionsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

   @Override
    public void onClick(View v) {
        fullView.openDrawer(Gravity.LEFT);
    }

    public ActivityComponent activityComponent() {
        return mActivityComponent;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", mActivityId);
            sComponentsMap.remove(mActivityId);
        }
        super.onDestroy();
    }


//    public void loadFragment(Fragment fragment) {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, fragment)
//                .commit();
//    }
//
//    public void loadFragment(Fragment fragment, String fragmentTag) {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragment_container, fragment, fragmentTag)
//                .commit();
//    }
}
