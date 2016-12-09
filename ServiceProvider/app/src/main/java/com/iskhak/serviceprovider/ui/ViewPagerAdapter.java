package com.iskhak.serviceprovider.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    JobsTab jobsTab;
    RequestsTab requestsTab;
    private String tabTitles[] = new String[] { "Request", "My Jobs" };

    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){

            if(requestsTab==null)
                requestsTab = new RequestsTab();
            return requestsTab;
        } else {
            if(jobsTab==null)
                jobsTab= new JobsTab();
            return jobsTab;
        }
    }



    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
