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
import com.iskhak.serviceprovider.helpers.RxUtil;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import butterknife.OnLongClick;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class RequestsTab extends Fragment {
    @Inject
    DataManager mDataManager;
    private INewOrderSender sender;
    private Subscription mSubscription;
    @BindView(R.id.request_list_lv) ListView requestList;
    RequestAdapter requestAdapter;

    // TODO: Rename and change types and number of parameters
    public static RequestsTab newInstance(/*int param1, String param2*/) {
        RequestsTab fragment = new RequestsTab();
/*        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        ((MainActivity)getActivity()).activityComponent().inject(this);
        View view = inflater.inflate(R.layout.requests_tab, container, false);
        ButterKnife.bind(this,view);
        requestAdapter = new RequestAdapter(getActivity(), R.layout.order_item, new ArrayList<PackageModel>());
        requestList.setAdapter(requestAdapter);

        return view;
    }

    public void updateAll(){
        mDataManager.setViewed(new Date(0));
        DataHolder.getInstance().clearOrders();
        requestAdapter.clear();
        loadNewOrders();
    }

    public void loadNewOrders(){
        Timber.i("Starting sync...");

        if (!NetworkUtil.isNetworkConnected(getContext())) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(getContext(), SyncService.SyncOnConnectionAvailable.class, true);
            return;
        }

        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getRequestList()
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
                        update(order);
                    }
                });
    }

    @OnItemClick(R.id.request_list_lv)
    public void onItemClick(AdapterView<?> parent, View view, int position){
        FullOrderFragment orderFragment = FullOrderFragment.newInstance(position, FullOrderFragment.BY_POSITION);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, orderFragment).commit();
        ((MainActivity)getActivity()).showTabs(false);
        Log.d("Item click", DataHolder.getInstance().getOrderByPosition(position).address());
    }


    public void update(final PackageModel order) {
        if(DataHolder.getInstance().getOrderById(order.id())!=null)
            return;
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
        loadNewOrders();
    }
}
