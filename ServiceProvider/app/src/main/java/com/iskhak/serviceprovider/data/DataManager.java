package com.iskhak.serviceprovider.data;

import android.app.Application;
import android.provider.Settings;

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

import retrofit2.Response;
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
    String androidId;

    @Inject
    public DataManager(Application application, ConnectionService connectionService, DatabaseHelper databaseHelper){
        mConnectionService = connectionService;
        mDatabaseHelper = databaseHelper;
        androidId = Settings.Secure.getString(application.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    public Observable<PackageModel> getNewOrders(){

        return mConnectionService.getNewOrders(androidId , new PathDate(viewed))
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

    public Date getLastViewed(){
        return viewed;
    }


    public Observable<Response<Void>> acceptOrder(Integer pkgId){
        return mConnectionService.acceptOrder(pkgId);
    }

    public String getAndroidId(){
        return androidId;
    }
}
