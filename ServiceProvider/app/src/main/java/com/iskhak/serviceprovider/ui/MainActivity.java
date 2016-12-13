package com.iskhak.serviceprovider.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.data.DataManager;
import com.iskhak.serviceprovider.data.SyncService;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.helpers.DataHolder;

import java.util.Stack;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity{

    public static final String ORDER_KEY = "orderKey";
    public static final int DEFAULT_ORDER = -1;

    @Inject
    DataManager mDataManager;

    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tab_layout)
    View tablayout;
    @BindView(R.id.fragment_container)
    View fragmentContainer;
    private ViewPagerAdapter viewPagerAdapter;
    private Stack<Fragment> backStack;
    private PackageModel order;
    private boolean isTabsVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataHolder.getInstance().setContext(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent!=null){
            int id = intent.getIntExtra(ORDER_KEY, DEFAULT_ORDER);
            if(id!=DEFAULT_ORDER) {
                order = DataHolder.getInstance().getOrderById(id);
                FullOrderFragment orderFragment = FullOrderFragment.newInstance(id, FullOrderFragment.BY_ID);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, orderFragment).commit();
                showTabs(false);
            } else {
                viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(viewPagerAdapter);

                tabLayout.addTab(tabLayout.newTab());
                tabLayout.addTab(tabLayout.newTab());

                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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
}
