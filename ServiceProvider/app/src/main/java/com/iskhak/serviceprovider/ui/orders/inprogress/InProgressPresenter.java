package com.iskhak.serviceprovider.ui.orders.inprogress;

import android.content.Context;
import android.os.Handler;

import com.iskhak.serviceprovider.data.DataManager;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.helpers.DataHolder;
import com.iskhak.serviceprovider.helpers.RxUtil;
import com.iskhak.serviceprovider.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class InProgressPresenter extends BasePresenter<InProgressMvpView>{
    private Subscription mSubscription;
    private DataManager mDataManager;
    @Inject
    public InProgressPresenter(DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void attachView(InProgressMvpView inProgressMvpView){
        super.attachView(inProgressMvpView);
    }
    @Override
    public void detachView(){
        super.detachView();
    }

    public void loadInProgressList() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getInProgressList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<PackageModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading jobs in progress list.");
                    }

                    @Override
                    public void onNext(PackageModel order) {
                        getMvpView().showInProgressItem(order);
                    }
                });
    }
}
