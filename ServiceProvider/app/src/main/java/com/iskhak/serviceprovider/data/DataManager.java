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
import com.iskhak.serviceprovider.helpers.RxObservableList;
import com.iskhak.serviceprovider.helpers.RxUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Singleton
public class DataManager {
    private final ConnectionService mConnectionService;
    private final DatabaseHelper mDatabaseHelper;
    private Subscription mSubscribe;
    private Date viewed = new Date(0);
    private String androidId;
    private RxObservableList<PackageModel> requestList;
    private RxObservableList<PackageModel> jobList;

    @Inject
    public DataManager(Application application, ConnectionService connectionService, DatabaseHelper databaseHelper){
        mConnectionService = connectionService;
        mDatabaseHelper = databaseHelper;
        androidId = Settings.Secure.getString(application.getContentResolver(), Settings.Secure.ANDROID_ID);
        requestList = new RxObservableList<>();
        jobList = new RxObservableList<>();
    }

    public Observable<Void> getNewOrders(){
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                requestList.clear();
                jobList.clear();
                mSubscribe = mConnectionService.getNewOrders(androidId , new PathDate(viewed))
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<List<PackageModel>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(List<PackageModel> packageModels) {
                                for(PackageModel packageModel: packageModels){
                                    if(packageModel.acceptedDate()==null){
                                        if(!requestList.contains(packageModel))
                                            requestList.add(packageModel);
                                    }else{
                                        if(!jobList.contains(packageModel))
                                            jobList.add(packageModel);
                                    }
                                }
                            }
                        });
                subscriber.onCompleted();
            }
        });
    }

    public Observable<PackageModel> getRequestList(){
        return requestList.getObservable();
    }

    public Observable<PackageModel> getJobList(){
        return jobList.getObservable();
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
