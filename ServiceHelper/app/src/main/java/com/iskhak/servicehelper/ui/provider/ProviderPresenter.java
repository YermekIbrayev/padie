package com.iskhak.servicehelper.ui.provider;

import com.iskhak.servicehelper.data.DataManager;
import com.iskhak.servicehelper.data.model.Provider;
import com.iskhak.servicehelper.data.model.ReviewModel;
import com.iskhak.servicehelper.helpers.RxUtil;
import com.iskhak.servicehelper.ui.base.BasePresenter;
import com.iskhak.servicehelper.ui.chooseprovider.ChooseProviderMvpView;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ProviderPresenter extends BasePresenter<ProviderMvpView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ProviderPresenter (DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ProviderMvpView mvpView){
        super.attachView(mvpView);
    }

    @Override
    public void detachView(){
        super.detachView();
        RxUtil.unsubscribe(mSubscription);
    }

    public void loadReviews(int pid){
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getFullReviewList(pid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<ReviewModel>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the providers.");
                        getMvpView().showLoadReviewsError();
                    }

                    @Override
                    public void onNext(List<ReviewModel> reviews) {
                        if(reviews.isEmpty()){
                            getMvpView().showReviewsEmpty();
                        } else {
                            getMvpView().showReviews(reviews);
                        }
                    }
                });
    }
}
