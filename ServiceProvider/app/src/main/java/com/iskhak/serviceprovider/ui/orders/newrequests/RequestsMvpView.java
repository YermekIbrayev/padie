package com.iskhak.serviceprovider.ui.orders.newrequests;

import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.ui.base.MvpView;

public interface RequestsMvpView extends MvpView {
    void showRequestItem(PackageModel inProgressList);
}
