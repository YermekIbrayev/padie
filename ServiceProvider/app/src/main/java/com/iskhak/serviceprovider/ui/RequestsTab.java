package com.iskhak.serviceprovider.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.data.DataManager;
import com.iskhak.serviceprovider.data.SyncService;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.helpers.AndroidComponentUtil;
import com.iskhak.serviceprovider.helpers.DataHolder;
import com.iskhak.serviceprovider.helpers.INewOrderListener;
import com.iskhak.serviceprovider.helpers.INewOrderSender;
import com.iskhak.serviceprovider.helpers.NetworkUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemLongClick;
import butterknife.OnLongClick;
import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class RequestsTab extends Fragment implements INewOrderListener{
    @Inject
    DataManager mDataManager;
    private INewOrderSender sender;
    private Subscription mSubscription;
    @BindView(R.id.request_list_lv) ListView requestList;
    RequestAdapter requestAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).pushBackstack(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        ((MainActivity)getActivity()).activityComponent().inject(this);
        View view = inflater.inflate(R.layout.requests_tab, container, false);
        ButterKnife.bind(this,view);
        requestAdapter = new RequestAdapter(getActivity(), R.layout.order_item, new ArrayList<PackageModel>());
        requestList.setAdapter(requestAdapter);
        loadNewOrders();
        sender = DataHolder.getInstance().getSender();

        return view;
    }

    private void loadNewOrders(){

        Timber.i("Starting sync...");

        if (!NetworkUtil.isNetworkConnected(getContext())) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(getContext(), SyncService.SyncOnConnectionAvailable.class, true);
            return;
        }

/*        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
        mSubscription = mDataManager.getNewOrders()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PackageModel>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("Synced successfully!");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.w(e, "Error syncing.");

                    }

                    @Override
                    public void onNext(PackageModel order) {
                        requestAdapter.add(order);
                    }
                });*/
    }
    @OnItemLongClick(R.id.request_list_lv)
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
        FullOrderFragment orderFragment = FullOrderFragment.newInstance(position, FullOrderFragment.BY_POSITION);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, orderFragment).commit();
        ((MainActivity)getActivity()).showFragmentContainer();
        Log.d("Long click", DataHolder.getInstance().getOrderByPosition(position).address());
        return true;
    }

    @Override
    public void update(final PackageModel order) {
        Handler mainHandler = new Handler(getContext().getMainLooper());
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                requestAdapter.add(order);
                DataHolder.getInstance().updateOrders(requestAdapter.getSize()-1, order);
            }
        };

        mainHandler.post(runnable);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(sender==null)
            sender = DataHolder.getInstance().getSender();
        sender.setListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        sender.removeListener(this);
    }
}
