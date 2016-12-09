package com.iskhak.serviceprovider.data.local;

import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.data.model.ResponseOrder;
import com.iskhak.serviceprovider.data.model.ServiceGroup;
import com.iskhak.serviceprovider.helpers.DataHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import timber.log.Timber;

@Singleton
public class DatabaseHelper {

    private List<ResponseOrder> responseOrders;

    @Inject
    public DatabaseHelper(){
    }

    public Observable<ServiceGroup> setServices(final Collection<ServiceGroup> newServices){
        return Observable.create(new Observable.OnSubscribe<ServiceGroup>(){

            @Override
            public void call(Subscriber<? super ServiceGroup> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                for(ServiceGroup serviceGroup: newServices){
                    Timber.i(serviceGroup.name());
                    subscriber.onNext(serviceGroup);
                }

                subscriber.onCompleted();
            }
        });
    }

    public Observable<PackageModel> getNewOrders(final Collection<PackageModel> newOrders){
        return Observable.create(new Observable.OnSubscribe<PackageModel>() {
            @Override
            public void call(Subscriber<? super PackageModel> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                List<PackageModel> orders = new ArrayList<>();
                for (PackageModel order:newOrders) {
                    Timber.i(order.toString());
                    orders.add(order);
                    subscriber.onNext(order);
                }
                //DataHolder.getInstance().setOrders(orders);
                subscriber.onCompleted();
            }
        });
    }
}
