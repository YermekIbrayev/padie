package com.iskhak.serviceprovider.ui.orders.pageadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iskhak.serviceprovider.ui.orders.OnClickCallback;
import com.iskhak.serviceprovider.ui.orders.finished.FinishedTab;
import com.iskhak.serviceprovider.ui.orders.inprogress.InProgressTab;
import com.iskhak.serviceprovider.ui.orders.newrequests.RequestsTab;

import timber.log.Timber;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final int TABS_COUNT = 3;

    private String tabTitles[] = new String[] { "New Requests", "In Progress", "Done" };
    private OnClickCallback mCallback;
    private RequestsTab mRequestsTab;
    private InProgressTab mInProgressTab;
    private FinishedTab mFinishedTab;

    public ViewPagerAdapter(FragmentManager fm, OnClickCallback callback){
        super(fm);
        mCallback = callback;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if(mRequestsTab==null)
                    mRequestsTab = RequestsTab.newInstance(mCallback);
                return mRequestsTab;
            case 1:
                if(mInProgressTab==null)
                    mInProgressTab = InProgressTab.newInstance(mCallback);
                return mInProgressTab;
            case 2:
                if(mFinishedTab==null)
                    mFinishedTab = FinishedTab.newInstance(mCallback);
                return mFinishedTab;
            default:
                return null;
        }
    }

    public void clearTabs(){
        mRequestsTab.clear();
        mInProgressTab.clear();
        mFinishedTab.clear();
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
