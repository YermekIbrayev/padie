package com.iskhak.serviceprovider.data;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.google.common.collect.ForwardingList;
import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.data.local.DatabaseHelper;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.data.model.PathDate;
import com.iskhak.serviceprovider.data.model.ResponseOrder;
import com.iskhak.serviceprovider.data.model.ServiceGroup;
import com.iskhak.serviceprovider.data.remote.ConnectionService;
import com.iskhak.serviceprovider.helpers.DataHolder;
import com.iskhak.serviceprovider.helpers.INewOrderSender;
import com.iskhak.serviceprovider.helpers.RxObservableList;
import com.iskhak.serviceprovider.helpers.RxUtil;
import com.iskhak.serviceprovider.injection.ApplicationContext;
import com.iskhak.serviceprovider.ui.MainActivity;

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
    private Context mContext;

    @Inject
    public DataManager(Application application, ConnectionService connectionService, DatabaseHelper databaseHelper){
        mConnectionService = connectionService;
        mDatabaseHelper = databaseHelper;
        mContext = application.getApplicationContext();
        androidId = Settings.Secure.getString(application.getContentResolver(), Settings.Secure.ANDROID_ID);
        requestList = new RxObservableList<>();
        jobList = new RxObservableList<>();
    }

    public Observable<PackageModel> getNewOrders(){
        return Observable.create(new Observable.OnSubscribe<PackageModel>() {
            @Override
            public void call(final Subscriber<? super PackageModel> subscriber) {
                requestList.clear();
                jobList.clear();
                if(viewed==null)
                    viewed = new Date(0);
                mConnectionService.getNewOrders(androidId , new PathDate(viewed))
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
                                    }else{
                                        if(!jobList.contains(packageModel))
                                            jobList.add(packageModel);
                                    }
                                    if(packageModel.viewed()==null||(packageModel.viewed()!=null&&viewed.before(packageModel.viewed()))) {
                                        generateViewedOrder(packageModel);
                                    }

                                }
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

    //-------- private helper methods;
    // exception when activity terminated should be resolved
    private void sendNotif(PackageModel order) {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(MainActivity.ORDER_KEY,order.id());
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(mContext)
                .setContentTitle("New Order:"+order.id())
                .setContentText("Service Price:"+order.price())
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();


        NotificationManager notificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(order.id(), n);

    }

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
