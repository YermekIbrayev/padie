package com.iskhak.servicehelper.ui.provider;

import com.iskhak.servicehelper.data.model.Provider;
import com.iskhak.servicehelper.data.model.ReviewModel;
import com.iskhak.servicehelper.ui.base.MvpView;

import java.util.List;

public interface ProviderMvpView extends MvpView{
    void showReviews(List<ReviewModel> providers);
    void showReviewsEmpty();
    void showLoadReviewsError();
}
