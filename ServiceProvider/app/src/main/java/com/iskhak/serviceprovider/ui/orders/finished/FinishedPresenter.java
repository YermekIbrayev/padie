package com.iskhak.serviceprovider.ui.orders.finished;

import com.iskhak.serviceprovider.data.DataManager;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.helpers.RxUtil;
import com.iskhak.serviceprovider.ui.base.BasePresenter;
import com.iskhak.serviceprovider.ui.orders.inprogress.InProgressMvpView;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class FinishedPresenter extends BasePresenter<FinishedMvpView>{
    private Subscription mSubscription;
    private DataManager mDataManager;
    @Inject
    public FinishedPresenter(DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void attachView(FinishedMvpView finishedMvpView){
        super.attachView(finishedMvpView);
    }
    @Override
    public void detachView(){
        super.detachView();
    }

    public void loadFinishedList() {
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getFinishedList()
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
                        getMvpView().showFinishedItem(order);
                    }
                });
    }
}
