package com.iskhak.serviceprovider.helpers;

import com.iskhak.serviceprovider.data.model.PackageModel;

public interface INewOrderListener {
    void update(PackageModel order);
}
