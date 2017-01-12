package com.iskhak.serviceprovider.ui.orders.pageadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iskhak.serviceprovider.ui.orders.OnClickCallback;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int TABS_COUNT = 2;

    private String tabTitles[] = new String[] { "New Requests", "In Progress", "Done" };
    private OnClickCallback mCallback;

    public ViewPagerAdapter(FragmentManager fm, OnClickCallback callback){
        super(fm);
        mCallback = callback;
    }

    @Override
    public Fragment getItem(int position) {
        return PageTab.getTabByPosition(position, mCallback);
    }

    public void clearTabs(){
        PageTab.clear();
    }

    @Override
    public int getCount() {
        return TABS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
