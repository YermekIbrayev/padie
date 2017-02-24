package com.iskhak.servicehelper.data;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.ServiceHelperApplication;
import com.iskhak.servicehelper.data.model.PackageModel;
import com.iskhak.servicehelper.data.model.ServiceGroup;
import com.iskhak.servicehelper.helpers.AndroidComponentHelper;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.helpers.NetworkUtil;
import com.iskhak.servicehelper.helpers.RxUtil;
import com.iskhak.servicehelper.ui.VerifyActivity;
import com.iskhak.servicehelper.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SyncService extends Service{

    private static final int REPEAT_COUNT = 3;
    private static final int LOOP_TIME = 30;
    private static final String LOGGED_IN_KEY="loggedIn";

    @Inject DataManager mDataManager;
    private static Context mContext;
    private Subscription mSubscription, mGetVerifyListSubscription;
    private Map<Integer, ServiceGroup> serviceGroups;

    public static Intent getStartIntent(Context context, boolean loggedIn){
        mContext = context;
        Intent intent = new Intent(context, SyncService.class);
        intent.putExtra(LOGGED_IN_KEY, loggedIn);
        return intent;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        ServiceHelperApplication.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId){
        Timber.i("Starting sync...");
        boolean loggedIn = intent.getBooleanExtra(LOGGED_IN_KEY, false);
        if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentHelper.toggleComponent(this, SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }

        getServiceList(startId);
        RxUtil.unsubscribe(mGetVerifyListSubscription);
        if(loggedIn)
            mGetVerifyListSubscription = getReviewNeededList(startId);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class SyncOnConnectionAvailable extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
                    && NetworkUtil.isNetworkConnected(context)) {
                Timber.i("Connection is now available, triggering sync...");
                AndroidComponentHelper.toggleComponent(context, this.getClass(), false);
                context.startService(getStartIntent(context, false));
            }
        }
    }

    //private functions

    private void getServiceList(final int startId){
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.syncServices()
                .subscribeOn(Schedulers.io())
                .retry()
                .subscribe(new Observer<ServiceGroup>() {
                    @Override
                    public void onCompleted() {
                        DataHolder.getInstance().setServiceList(serviceGroups);
                        Intent intent = LoginActivity.newStartIntent(mContext);
                        mContext.startActivity(intent);
                        Timber.i("Synced successfully!");
                        stopSelf(startId);
                        System.out.println("Operator thread: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.w(e, "Error syncing.");
                        stopSelf(startId);
                        System.out.println("Operator thread: " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(ServiceGroup serviceGroup) {
                        if(serviceGroups==null)
                            serviceGroups = new HashMap<>();
                        serviceGroups.put(serviceGroup.id(),serviceGroup);
                        System.out.println("Operator thread: " + Thread.currentThread().getName());
                    }
                });
    }

    private Subscription getReviewNeededList(final int startId){
        return mDataManager.getReviewNeededList()
               /* .observeOn(AndroidSchedulers.mainThread())*/
                .subscribeOn(Schedulers.io())
                .retry(REPEAT_COUNT)
                .unsubscribeOn(Schedulers.io())
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        Timber.d("Sync Repeat...");

                        return observable.delay(LOOP_TIME, TimeUnit.SECONDS);
                    }
                })

                .subscribe(new Observer<List<PackageModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "onGetOrders");
                        //stopSelf(startId);
                        showLoadingError();
                    }

                    @Override
                    public void onNext(List<PackageModel> orderList) {
                        for(PackageModel order:orderList)
                            sendNotification(order);
                    }
                });
    }

    private void sendNotification(PackageModel order) {
        Intent intent = VerifyActivity.newStartIntent(this, order);
                /*new Intent(this, OrdersActivity.class);
        intent.putExtra(OrdersActivity.ORDER_KEY,order.id());*/
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(this)
                .setContentTitle("New Order:"+order.id())
                .setContentText("Service Price:"+order.price())
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(order.id(), n);

    }

    private void showLoadingError(){

    }

}
