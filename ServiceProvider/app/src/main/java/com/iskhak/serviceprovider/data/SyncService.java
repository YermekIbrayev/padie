package com.iskhak.serviceprovider.data;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.ServiceProviderApplication;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.data.model.ResponseOrder;
import com.iskhak.serviceprovider.helpers.AndroidComponentUtil;
import com.iskhak.serviceprovider.helpers.DataHolder;
import com.iskhak.serviceprovider.helpers.INewOrderListener;
import com.iskhak.serviceprovider.helpers.INewOrderSender;
import com.iskhak.serviceprovider.helpers.NetworkUtil;
import com.iskhak.serviceprovider.ui.MainActivity;
import com.iskhak.serviceprovider.ui.RequestsTab;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Singleton
public class SyncService extends Service implements INewOrderSender {

    private static final int REPEAT_COUNT =3;
    private static final int LOOP_TIME = 30;

    @Inject DataManager mDataManager;
    private Subscription mSubscription;
    private Date lastUpdate;
    private List<INewOrderListener> mListeners;
    private String androidId;
    private Map<Integer, PackageModel> orders;
    private INewOrderSender sender;

    public static Intent getStartIntent(Context context){
        return new Intent(context, SyncService.class);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        ServiceProviderApplication.get(this).getComponent().inject(this);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId){
        Timber.i("Starting sync...");
        sender = this;
        DataHolder.getInstance().setSender(this);
        if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(this, SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }

        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
        if(lastUpdate==null)
            lastUpdate = new Date(0);
        mSubscription = getOrders(startId);

        return START_STICKY;
    }

    private Subscription getOrders(final int startId){
        return mDataManager.getNewOrders()
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

                .subscribe(new Observer<PackageModel>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("Synced successfully!");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.w(e, "Error syncing.");
                        stopSelf(startId);

                    }

                    @Override
                    public void onNext(PackageModel order) {
                        if(lastUpdate==null)
                            lastUpdate = new Date(0);
                        if(order.viewed()!=null&&order.viewed()!=null&&lastUpdate.before(order.viewed())) {
                            lastUpdate = order.viewed();
                            mDataManager.setViewed(lastUpdate);
                            if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
                            mSubscription = getOrders(startId);
                        }
                        if(orders==null) {
                            orders = new HashMap<>();
                            DataHolder.getInstance().setIndexedOrders(orders);
                        }
                        if(DataHolder.getInstance().getSender()!=null)
                            DataHolder.getInstance().setSender(sender);
                        if(mListeners!= null&&!orders.containsKey(order.id()))
                            for(INewOrderListener listner:mListeners)
                                listner.update(order);
                        if(!orders.containsKey(order.id()))
                            orders.put(order.id(), order);


                        if(order.viewed()==null&&(mListeners==null||mListeners.size()==0))
                            sendNotif(order);
                        if(order.viewed()==null)
                            setViewedOrders(order);
                        Timber.d("onNext");

                    }
                });
    }

    private void setViewedOrders(PackageModel order){
        ResponseOrder responseOrder = ResponseOrder.builder()
                .setId(order.id())
                .setDeviceId(androidId)
                .setSelectedPkg(order.id())
                .setViewed(new Date())
                .build();
        mDataManager.sendViewedOrders(responseOrder);
    }

    private void sendNotif(PackageModel order) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.ORDER_KEY,order.id());
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


    @Override
    public void onDestroy() {
        if (mSubscription != null) mSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void setListener(INewOrderListener listener) {
        if(mListeners==null)
            mListeners = new ArrayList<>();
        mListeners.add(listener);
    }

    @Override
    public void removeListener(INewOrderListener listener) {
        if(mListeners == null)
            return;
        if(mListeners.contains(listener))
            mListeners.remove(listener);
    }

    public static class SyncOnConnectionAvailable extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
                    && NetworkUtil.isNetworkConnected(context)) {
                Timber.i("Connection is now available, triggering sync...");
                AndroidComponentUtil.toggleComponent(context, this.getClass(), false);
                context.startService(getStartIntent(context));
            }
        }
    }
}
