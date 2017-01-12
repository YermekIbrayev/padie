package com.iskhak.serviceprovider.ui.orders.order;

import com.iskhak.serviceprovider.data.DataManager;
import com.iskhak.serviceprovider.data.model.PackageModel;
import com.iskhak.serviceprovider.helpers.RxUtil;
import com.iskhak.serviceprovider.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class OrderPresenter extends BasePresenter<OrderMvpView>{
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public OrderPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(OrderMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        RxUtil.unsubscribe(mSubscription);
    }

    public void onAcceptButton(PackageModel order){
        Timber.d("On Accept button");
        if (mSubscription != null && !mSubscription.isUnsubscribed()) mSubscription.unsubscribe();
        mSubscription = mDataManager.acceptOrder(order.id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<Void>>() {
                    @Override
                    public void onCompleted() {
                        mSubscription = null;
                        getMvpView().showAcceptComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "onError %s", e.toString());
                        getMvpView().showAcceptError();
                    }

                    @Override
                    public void onNext(Response<Void> voidResponse) {
                        Timber.d("onNext %s", voidResponse.code());
                    }
                });
    }

    public void onDoneButton(PackageModel order){

    }
}
