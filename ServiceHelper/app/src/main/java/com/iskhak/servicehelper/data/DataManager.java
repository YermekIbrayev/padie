package com.iskhak.servicehelper.data;

import com.iskhak.servicehelper.data.model.PackageModel;
import com.iskhak.servicehelper.data.model.SelectedItems;
import com.iskhak.servicehelper.data.model.SelectedItemsAdd;
import com.iskhak.servicehelper.data.model.SelectedItemsAddExtra;
import com.iskhak.servicehelper.data.remote.ConnectionService;
import com.iskhak.servicehelper.data.model.ServiceGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import timber.log.Timber;

@Singleton
public class DataManager {

    private ConnectionService mConnectionService;

    @Inject
    public DataManager(ConnectionService connectionService){
        mConnectionService = connectionService;
    }

    public Observable<ServiceGroup> syncServices(){
        return mConnectionService.getServices()
                .concatMap(new Func1<List<ServiceGroup>, Observable<? extends ServiceGroup>>() {
                    @Override
                    public Observable<? extends ServiceGroup> call(final List<ServiceGroup> serviceGroups) {
                        return Observable.create(new Observable.OnSubscribe<ServiceGroup>() {
                            @Override
                            public void call(Subscriber<? super ServiceGroup> subscriber) {
                                if(subscriber.isUnsubscribed()) return;
                                Timber.i("-----Data Manager----");
                                for(ServiceGroup serviceGroup:serviceGroups){
                                    subscriber.onNext(serviceGroup);
                                    Timber.i(serviceGroup.name());
                                }
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    public Observable<PackageModel> sendOrder(PackageModel order){
        int ts = 1;
        return mConnectionService.sendOrder(order)
                .concatMap(new Func1<PackageModel, Observable<? extends PackageModel>>() {
                    @Override
                    public Observable<? extends PackageModel> call(final PackageModel packageModel) {
                        return Observable.create(new Observable.OnSubscribe<PackageModel>() {
                            @Override
                            public void call(Subscriber<? super PackageModel> subscriber) {
                                Timber.i("---- sendOrder----------");
                                if(subscriber.isUnsubscribed()) return;
                                subscriber.onNext(packageModel);
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }


}
