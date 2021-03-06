package com.iskhak.serviceprovider.data;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.data.local.DatabaseHelper;
import com.iskhak.serviceprovider.data.model.LoginInfo;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.data.model.PathDate;
import com.iskhak.serviceprovider.data.model.ResponseOrder;
import com.iskhak.serviceprovider.data.model.TokenModel;
import com.iskhak.serviceprovider.data.remote.ConnectionService;
import com.iskhak.serviceprovider.helpers.DataHolder;
import com.iskhak.serviceprovider.helpers.RxObservableList;
import com.iskhak.serviceprovider.ui.orders.activity.OrdersActivity;


import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Singleton
public class DataManager {
    private final ConnectionService mConnectionService;
    private Date viewed = new Date(0);
    private String androidId;
    private RxObservableList<PackageModel> requestList;
    private RxObservableList<PackageModel> inProgressList;
    private RxObservableList<PackageModel> finishedList;

    @Inject
    public DataManager(Application application, ConnectionService connectionService){
        mConnectionService = connectionService;
        androidId = Settings.Secure.getString(application.getContentResolver(), Settings.Secure.ANDROID_ID);
        requestList = new RxObservableList<>();
        inProgressList = new RxObservableList<>();
        finishedList = new RxObservableList<>();
    }

    public Observable<PackageModel> getNewOrders(){
        final String token = DataHolder.getInstance().getToken().token();
        return Observable.create(new Observable.OnSubscribe<PackageModel>() {
            @Override
            public void call(final Subscriber<? super PackageModel> subscriber) {
                requestList.clear();
                inProgressList.clear();
                if(viewed==null)
                    viewed = new Date(0);
                mConnectionService.getNewOrders(token, androidId , new PathDate(viewed))
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<List<PackageModel>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Timber.e(e, "onGetNewOrders");
                            }

                            @Override
                            public void onNext(List<PackageModel> packageModels) {

                                for(PackageModel packageModel: packageModels){

                                    if(packageModel.acceptedDate()==null){
                                        if(!requestList.contains(packageModel))
                                            requestList.add(packageModel);
                                        if(!DataHolder.getInstance().isRunning()&&packageModel.viewed()==null)
                                            subscriber.onNext(packageModel);
                                    }else if(packageModel.finishedDate()==null){
                                        if(!inProgressList.contains(packageModel))
                                            inProgressList.add(packageModel);
                                    } else {
                                        if(!finishedList.contains(packageModel))
                                            finishedList.add(packageModel);
                                    }

                                    if(viewed==null)
                                        viewed = new Date(0);
                                    if(packageModel.viewed()==null||(packageModel.viewed()!=null&&viewed.before(packageModel.viewed()))) {
                                        generateViewedOrder(packageModel);
                                    }
                                }
                                Timber.d("request: " + requestList.getFullList().size());
                                Timber.d("inProgress: " + inProgressList.getFullList().size());
                                Timber.d("finishedList: " + finishedList.getFullList().size());
                            }
                        });
            }
        });
    }


    public Observable<PackageModel> getRequestList(){
        return requestList.getObservable();
    }

    public List<PackageModel> getFullRequestList(){
        return requestList.getFullList();
    }

    public Observable<PackageModel> getInProgressList(){
        return inProgressList.getObservable();
    }

    public Observable<PackageModel> getFinishedList() {
        return finishedList.getObservable();
    }

    public void clear(){
        requestList.clear();
        inProgressList.clear();
        finishedList.clear();
    }

    public void sendViewedOrders(final ResponseOrder responseOrder){
        String token = DataHolder.getInstance().getToken().token();
        try {
            mConnectionService.responseOrder(token, responseOrder).execute();
        } catch(Exception e){
            Timber.e(e, "sending viewed Orders");
        }
    }

    public void setViewed(Date date){
        viewed= date;
    }

    public Date getLastViewed(){
        return viewed;
    }

    public Observable<Response<Void>> acceptOrder(Integer pkgId){
        String token = DataHolder.getInstance().getToken().token();
        return mConnectionService.acceptOrder(token, pkgId);
    }

    public Observable<Response<Void>> doneOrder(Integer pkgId){
        String token = DataHolder.getInstance().getToken().token();
        return mConnectionService.doneOrder(token, pkgId);
    }

    public Observable<Response<TokenModel>> login(LoginInfo loginInfo){
        return mConnectionService.login(loginInfo);
    }

    public Observable<Response<TokenModel>> register(LoginInfo loginInfo){
        return mConnectionService.registration(loginInfo);
    }

    public String getAndroidId(){
        return androidId;
    }

    //-------- private helper methods;
    private void generateViewedOrder(PackageModel packageModel){
        viewed = packageModel.viewed();
        ResponseOrder responseOrder = ResponseOrder.builder()
                .setId(packageModel.id())
                .setDeviceId(androidId)
                .setSelectedPkg(packageModel.id())
                .setViewed(new Date())
                .build();
        sendViewedOrders(responseOrder);
    }
}
