package com.iskhak.servicehelper.data;

import com.iskhak.servicehelper.data.model.LoginInfo;
import com.iskhak.servicehelper.data.model.PackageModel;
import com.iskhak.servicehelper.data.model.Provider;
import com.iskhak.servicehelper.data.model.ReviewModel;
import com.iskhak.servicehelper.data.model.SelectedItems;
import com.iskhak.servicehelper.data.model.SelectedItemsAdd;
import com.iskhak.servicehelper.data.model.SelectedItemsAddExtra;
import com.iskhak.servicehelper.data.model.TokenModel;
import com.iskhak.servicehelper.data.remote.ConnectionService;
import com.iskhak.servicehelper.data.model.ServiceGroup;
import com.iskhak.servicehelper.helpers.DataHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import timber.log.Timber;

@Singleton
public class DataManager {

    private final ConnectionService mConnectionService;

    @Inject
    public DataManager(ConnectionService connectionService){
        mConnectionService = connectionService;
    }

    public Observable<Response<TokenModel>> register(LoginInfo loginInfo){
        return mConnectionService.registration(loginInfo);
    }

    public Observable<List<Provider>> getProviders(){
        String token = DataHolder.getInstance().getToken().token();
        return mConnectionService.getProviders(token);
    }

    public Observable<List<ReviewModel>> getShortReviewList(Integer pid){
        String token = DataHolder.getInstance().getToken().token();
        return mConnectionService.getShortReviewList(token,pid);
    }

    public Observable<List<ReviewModel>> getFullReviewList(Integer pid){
        String token = DataHolder.getInstance().getToken().token();
        return mConnectionService.getFullReviewList(token, pid);
    }

    public Observable<Response<Void>> verifyOrder(Integer pkgId){
        String token = DataHolder.getInstance().getToken().token();
        return mConnectionService.verifyOrder(token, pkgId);
    }

    public Observable<Response<Void>>  sendReview(ReviewModel review){
        String token = DataHolder.getInstance().getToken().token();
        return mConnectionService.sendReview(token, review);
    }

    public Observable<List<PackageModel>> getReviewNeededList(){
        String token = "";
        if(DataHolder.getInstance().getToken()!=null)
           token = DataHolder.getInstance().getToken().token();
        return mConnectionService.getReviewNeededList(token);

    }

    public Observable<ServiceGroup> syncServices(){
        return mConnectionService.getServices()
                .concatMap(new Func1<List<ServiceGroup>, Observable<? extends ServiceGroup>>() {
                    @Override
                    public Observable<? extends ServiceGroup> call(final List<ServiceGroup> serviceGroups) {
                        return Observable.create(new Observable.OnSubscribe<ServiceGroup>() {
                            @Override
                            public void call(Subscriber<? super ServiceGroup> subscriber) {
                                if(subscriber.isUnsubscribed()) return;
                                Timber.i("-----Data Manager----");
                                for(ServiceGroup serviceGroup:serviceGroups){
                                    subscriber.onNext(serviceGroup);
                                    Timber.i(serviceGroup.name());
                                }
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    public Observable<PackageModel> sendOrder(PackageModel order){
        String token = DataHolder.getInstance().getToken().token();
        Timber.d("providerId:" + order.providerID());
        return mConnectionService.sendOrder(token, order)
                .concatMap(new Func1<PackageModel, Observable<? extends PackageModel>>() {
                    @Override
                    public Observable<? extends PackageModel> call(final PackageModel packageModel) {
                        return Observable.create(new Observable.OnSubscribe<PackageModel>() {
                            @Override
                            public void call(Subscriber<? super PackageModel> subscriber) {
                                Timber.i("---- sendOrder----------");
                                if(subscriber.isUnsubscribed()) return;
                                subscriber.onNext(packageModel);
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    public Observable<PackageModel> getOrderPrice(PackageModel order){
        String token = DataHolder.getInstance().getToken().token();
        return mConnectionService.getOrderPrice(token, order)
                .concatMap(new Func1<PackageModel, Observable<? extends PackageModel>>() {
                    @Override
                    public Observable<? extends PackageModel> call(final PackageModel packageModel) {
                        return Observable.create(new Observable.OnSubscribe<PackageModel>() {
                            @Override
                            public void call(Subscriber<? super PackageModel> subscriber) {
                                Timber.i("---- getOrderPrice ----------");
                                if(subscriber.isUnsubscribed()) return;
                                subscriber.onNext(packageModel);
                                subscriber.onCompleted();
                            }
                        });
                    }
                });
    }

    public Observable<Response<TokenModel>> login(LoginInfo loginInfo){
        return mConnectionService.login(loginInfo);
    }


}
