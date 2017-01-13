package com.iskhak.serviceprovider.ui.orders.finished;

import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.ui.base.MvpView;

public interface FinishedMvpView extends MvpView {
    void showFinishedItem(PackageModel order);
}
