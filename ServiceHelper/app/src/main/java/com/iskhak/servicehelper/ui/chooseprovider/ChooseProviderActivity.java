package com.iskhak.servicehelper.ui.chooseprovider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.iskhak.servicehelper.R;
import com.iskhak.servicehelper.data.model.Provider;
import com.iskhak.servicehelper.helpers.DataHolder;
import com.iskhak.servicehelper.helpers.DialogFactory;
import com.iskhak.servicehelper.ui.base.BaseActivity;
import com.iskhak.servicehelper.ui.provider.ProviderActivity;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ChooseProviderActivity extends BaseActivity implements ChooseProviderMvpView, ProvidersAdapter.Callback{

    @Inject
    ProvidersAdapter mProvidersAdapter;

    @Inject
    ChooseProviderPresenter mChooseProviderPresenter;

    @BindView(R.id.recycler_view_choose_provider)
    RecyclerView mRecyclerView;

    public static Intent getStartIntent(Context context){
        Intent intent = new Intent(context, ChooseProviderActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_choose_provider);
        ButterKnife.bind(this);
        mProvidersAdapter.setCallback(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ChooseProviderActivity.this));
        mRecyclerView.setAdapter(mProvidersAdapter);
        mChooseProviderPresenter.attachView(this);
        mChooseProviderPresenter.loadProviders();
    }

    /***** MVP View methods implementation *****/
    @Override
    public void showProviders(List<Provider> providers) {
        mProvidersAdapter.setProviders(providers);
        mProvidersAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProvidersEmpty() {
        mProvidersAdapter.setProviders(Collections.<Provider>emptyList());
        mProvidersAdapter.notifyDataSetChanged();
        DialogFactory.createGenericErrorDialog(
                ChooseProviderActivity.this, R.string.error_no_providers)
                .show();
    }

    @Override
    public void showLoadProviderError() {
        DialogFactory.createGenericErrorDialog(
                ChooseProviderActivity.this, R.string.error_loading_providers)
                .show();
    }

    /******* ProviderAdapter callback *******/
    @Override
    public void onProviderClicked(Provider provider) {
        startActivity(ProviderActivity.getStartIntent(this, provider));
    }
}
