package com.iskhak.serviceprovider.ui.orders.order;

import com.iskhak.serviceprovider.ui.base.MvpView;

public interface OrderMvpView extends MvpView {
    void showAcceptComplete();
    void showAcceptError();
    void showDoneComplete();
    void showDoneError();
}
