package com.iskhak.servicehelper.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.iskhak.servicehelper.ServiceHelperApplication;
import com.iskhak.servicehelper.data.model.ServiceGroup;
import com.iskhak.servicehelper.data.remote.ConnectionService;
import com.iskhak.servicehelper.helpers.AndroidComponentHelper;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.helpers.NetworkHelper;
import com.iskhak.servicehelper.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SyncService extends Service{

    @Inject DataManager mDataManager;
    private static Context mContext;
    private Subscription mSubscription;
    private List<ServiceGroup> serviceGroups;

    public static Intent getStartIntent(Context context){
        mContext = context;
        return new Intent(context, SyncService.class);
    }

    @Override
    public void onCreate(){
        super.onCreate();
        ServiceHelperApplication.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId){
        Timber.i("Starting sync...");


        if (!NetworkHelper.isNetworkConnected(this)) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentHelper.toggleComponent(this, SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }

        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
        mSubscription = mDataManager.syncServices()
                .subscribeOn(Schedulers.newThread())
                .retry()
                .subscribe(new Observer<ServiceGroup>() {
                    @Override
                    public void onCompleted() {
                        DataHolder.getInstance().setServiceList(serviceGroups);
                        Intent intent = new Intent(mContext, MainActivity.class);
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
                            serviceGroups = new ArrayList<>();
                        serviceGroups.add(serviceGroup);
                        System.out.println("Operator thread: " + Thread.currentThread().getName());
                    }
                });

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
                    && NetworkHelper.isNetworkConnected(context)) {
                Timber.i("Connection is now available, triggering sync...");
                AndroidComponentHelper.toggleComponent(context, this.getClass(), false);
                context.startService(getStartIntent(context));
            }
        }
    }
}
