package com.iskhak.serviceprovider.ui.orders.inprogress;

import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.ui.base.MvpView;

public interface InProgressMvpView extends MvpView {
    void showInProgressItem(PackageModel inProgressList);
}
