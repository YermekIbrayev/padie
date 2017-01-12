package com.iskhak.serviceprovider.ui.orders.pageadapter;

import android.support.v4.app.Fragment;

import com.iskhak.serviceprovider.ui.orders.OnClickCallback;
import com.iskhak.serviceprovider.ui.orders.inprogress.InProgressTab;
import com.iskhak.serviceprovider.ui.orders.newrequests.RequestsTab;

public class PageTab {
    private static InProgressTab inProgressTab;
    private static RequestsTab requestsTab;
    public static Fragment getTabByPosition(int position, OnClickCallback callback){
        if(position==0){

            if(requestsTab==null)
                requestsTab = RequestsTab.newInstance(callback);
            return requestsTab;
        } else {
            if(inProgressTab==null)
                inProgressTab= InProgressTab.newInstance(callback);
            return inProgressTab;
        }
    }

    public static void clear(){
        if(inProgressTab!=null)
            inProgressTab.clear();
        if(requestsTab!=null)
            requestsTab.clear();
    }
}
