package com.iskhak.serviceprovider.ui.orders.finished;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iskhak.serviceprovider.R;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.ui.base.BaseActivity;
import com.iskhak.serviceprovider.ui.orders.OnClickCallback;
import com.iskhak.serviceprovider.ui.orders.RecyclerViewAdapter;
import com.iskhak.serviceprovider.ui.orders.order.OrderFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinishedTab extends Fragment
        implements FinishedMvpView, OnClickCallback{

    @Inject
    RecyclerViewAdapter mRecyclerViewAdapter;

    @Inject
    FinishedPresenter mFinishedPresenter;

    private OnClickCallback mCallback;

    @BindView(R.id.tab_finished_recycler_view)
    RecyclerView mRecyclerView;

    // TODO: Rename and change types and number of parameters
    public static FinishedTab newInstance(OnClickCallback callback) {
        FinishedTab fragment = new FinishedTab();
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
        View view = inflater.inflate(R.layout.tab_finished, container, false);
        ButterKnife.bind(this, view);
        mRecyclerViewAdapter.setOnClickCallback(this);
        mRecyclerViewAdapter.setState(OrderFragment.State.DONE);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFinishedPresenter.attachView(this);
        mFinishedPresenter.loadFinishedList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mFinishedPresenter.loadFinishedList();
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
    public void showFinishedItem(final PackageModel order) {
        mRecyclerViewAdapter.add(order);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void clear(){
        mRecyclerViewAdapter.clear();
    }
}
