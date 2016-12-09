package com.iskhak.serviceprovider.helpers;

public interface INewOrderSender {
    void setListener(INewOrderListener listener);
    void removeListener(INewOrderListener listener);
}
