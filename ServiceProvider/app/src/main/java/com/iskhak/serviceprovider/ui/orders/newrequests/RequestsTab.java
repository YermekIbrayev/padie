package com.iskhak.serviceprovider.ui.orders.newrequests;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.iskhak.serviceprovider.helpers.INewOrderSender;
import com.iskhak.serviceprovider.helpers.NetworkUtil;
import com.iskhak.serviceprovider.helpers.RxUtil;
import com.iskhak.serviceprovider.ui.base.BaseActivity;
import com.iskhak.serviceprovider.ui.orders.OnClickCallback;
import com.iskhak.serviceprovider.ui.orders.RecyclerViewAdapter;
import com.iskhak.serviceprovider.ui.orders.activity.OrdersActivity;
import com.iskhak.serviceprovider.ui.orders.inprogress.InProgressPresenter;
import com.iskhak.serviceprovider.ui.orders.inprogress.InProgressTab;
import com.iskhak.serviceprovider.ui.orders.order.OrderFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import rx.Observer;
import rx.Subscription;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class RequestsTab extends Fragment
        implements RequestsMvpView, OnClickCallback{

    @Inject
    RecyclerViewAdapter mRecyclerViewAdapter;

    @Inject
    RequestPresenter mRequestPresenter;

    private OnClickCallback mCallback;

    @BindView(R.id.in_progress_recycler_view)
    RecyclerView mRecyclerView;

    // TODO: Rename and change types and number of parameters
    public static RequestsTab newInstance(OnClickCallback callback) {
        RequestsTab fragment = new RequestsTab();
        fragment.setOnClickCallback(callback);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ((BaseActivity)getActivity()).activityComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_in_progress, container, false);
        ButterKnife.bind(this, view);
        mRecyclerViewAdapter.setOnClickCallback(this);
        mRecyclerViewAdapter.setState(OrderFragment.State.NEW);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRequestPresenter.attachView(this);
        mRequestPresenter.loadRequestList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mRequestPresenter.loadRequestList();
    }

    public void setOnClickCallback(OnClickCallback callback){
        mCallback = callback;
    }

    @Override
    public void onItemClicked(PackageModel order, OrderFragment.State state) {
        if(mCallback!=null)
            mCallback.onItemClicked(order, state);
    }

    @Override
    public void showRequestItem(final PackageModel order) {
        mRecyclerViewAdapter.add(order);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void clear(){
        mRecyclerViewAdapter.clear();
    }
}
