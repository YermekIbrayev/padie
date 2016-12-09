package com.iskhak.serviceprovider.data;

import com.google.common.collect.ForwardingList;
import com.iskhak.serviceprovider.data.local.DatabaseHelper;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.data.model.PathDate;
import com.iskhak.serviceprovider.data.model.ResponseOrder;
import com.iskhak.serviceprovider.data.model.ServiceGroup;
import com.iskhak.serviceprovider.data.remote.ConnectionService;
import com.iskhak.serviceprovider.helpers.INewOrderSender;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Singleton
public class DataManager {
    private final ConnectionService mConnectionService;
    private final DatabaseHelper mDatabaseHelper;
    private Date viewed = new Date(0);

    private Map<Integer, PackageModel> orders;

    @Inject
    public DataManager(ConnectionService connectionService, DatabaseHelper databaseHelper){
        mConnectionService = connectionService;
        mDatabaseHelper = databaseHelper;
    }

    public Observable<ServiceGroup> syncServiceList(){
        return mConnectionService.getServices()
                .concatMap(new Func1<List<ServiceGroup>, Observable< ServiceGroup>>() {
                    @Override
                    public Observable<ServiceGroup> call(List<ServiceGroup> serviceGroupList) {
                        Timber.i("Sync success");
                        return mDatabaseHelper.setServices(serviceGroupList);
                    }
                });
    }



    public Observable<PackageModel> getNewOrders(String deviceId){

        return mConnectionService.getNewOrders(deviceId, new PathDate(viewed))
                .concatMap(new Func1<List<PackageModel>, Observable<? extends PackageModel>>() {
                    @Override
                    public Observable<? extends PackageModel> call(List<PackageModel> packageModels) {
                        Timber.i("Sync success");
                        return mDatabaseHelper.getNewOrders(packageModels);
                    }
                });
    }

    public void sendViewedOrders(final ResponseOrder responseOrder){
        try {
            mConnectionService.responseOrder(responseOrder).execute();
        } catch(Exception e){
            Timber.e(e, "send Viewed Orders");
        }
    }

    public void setViewed(Date date){
        viewed= date;
    }



    public void setOrder(Map<Integer, PackageModel> orders){
        this.orders = orders;
    }

    public PackageModel getOrder(int id){
        return orders.get(id);
    }
}
