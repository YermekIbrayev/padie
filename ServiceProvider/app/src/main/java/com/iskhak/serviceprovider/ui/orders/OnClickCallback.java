package com.iskhak.serviceprovider.ui.orders;

import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.ui.orders.order.OrderFragment;

public interface OnClickCallback {
    void onItemClicked(PackageModel order, OrderFragment.State state);
}
