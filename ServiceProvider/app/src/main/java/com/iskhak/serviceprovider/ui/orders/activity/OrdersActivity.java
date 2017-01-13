package com.iskhak.serviceprovider.ui.orders.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.data.DataManager;
import com.iskhak.serviceprovider.data.SyncService;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.helpers.DataHolder;
import com.iskhak.serviceprovider.ui.orders.OnClickCallback;
import com.iskhak.serviceprovider.ui.orders.order.OrderFragment;
import com.iskhak.serviceprovider.ui.orders.pageadapter.ViewPagerAdapter;
import com.iskhak.serviceprovider.ui.base.BaseActivity;

import java.util.Date;
import java.util.Stack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class OrdersActivity extends BaseActivity implements OrdersMvpView, OnClickCallback{

    public static final String ORDER_KEY = "orderKey";

    @Inject
    DataManager mDataManager;

    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.fragment_container)
    View fragmentContainer;
    private ViewPagerAdapter viewPagerAdapter;
    private PackageModel selectedOrder;
    private boolean isTabsVisible;


    public static Intent newStartIntent(Context context, PackageModel order) {
        Intent intent = new Intent(context, OrdersActivity.class);
        intent.putExtra(ORDER_KEY, order);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        DataHolder.getInstance().setContext(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent!=null){
            selectedOrder = intent.getParcelableExtra(ORDER_KEY);
            if(selectedOrder!=null) {
                showOrderFragment(selectedOrder, OrderFragment.State.NEW);
            } else {
                viewPagerAdapter = new ViewPagerAdapter(this.getSupportFragmentManager(), this);
                viewPager.setAdapter(viewPagerAdapter);

                tabLayout.addTab(tabLayout.newTab());
                tabLayout.addTab(tabLayout.newTab());
                tabLayout.addTab(tabLayout.newTab());

                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

                tabLayout.setupWithViewPager(viewPager);
            }
        }
        startService(SyncService.getStartIntent(this));
    }

    public void showTabs(boolean isTabsVisible){
        this.isTabsVisible = isTabsVisible;
        if(isTabsVisible) {
            tabLayout.setVisibility(View.VISIBLE);
            fragmentContainer.setVisibility(View.GONE);
        } else {
            tabLayout.setVisibility(View.GONE);
            fragmentContainer.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {

        if (isTabsVisible) {
            super.onBackPressed();
            //additional code
        } else {
            showTabs(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataHolder.getInstance().startActivity();
    }

    @Override
    protected void onPause() {
        super.onPause();
        DataHolder.getInstance().stopActivity();
    }

    @Override
    public void onItemClicked(PackageModel order, OrderFragment.State state) {
        selectedOrder = order;
        showOrderFragment(order, state);
    }

    public void updateAll() {
        viewPagerAdapter.clearTabs();
        mDataManager.clear();
        mDataManager.setViewed(new Date(0));
        mDataManager.getNewOrders().subscribe();
    }

    //**** private functions
    private void showOrderFragment(PackageModel order, OrderFragment.State state){
        loadFragment(OrderFragment.newInstance(order, state));
        showTabs(false);
    }
}
