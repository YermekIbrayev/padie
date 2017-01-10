package com.iskhak.servicehelper.ui.chooseprovider;

import com.iskhak.servicehelper.data.DataManager;
import com.iskhak.servicehelper.data.model.Provider;
import com.iskhak.servicehelper.helpers.RxUtil;
import com.iskhak.servicehelper.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ChooseProviderPresenter extends BasePresenter<ChooseProviderMvpView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ChooseProviderPresenter (DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ChooseProviderMvpView mvpView){
        super.attachView(mvpView);
    }

    @Override
    public void detachView(){
        super.detachView();
        RxUtil.unsubscribe(mSubscription);
    }

    public void loadProviders(){
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getProviders()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Provider>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the event logs.");
                        getMvpView().showLoadProviderError();
                    }

                    @Override
                    public void onNext(List<Provider> providers) {
                        if(providers.isEmpty()){
                            getMvpView().showProvidersEmpty();
                        } else {
                            getMvpView().showProviders(providers);
                        }
                    }
                });
    }
}
