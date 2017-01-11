package com.iskhak.servicehelper.ui.chooseprovider;

import com.iskhak.servicehelper.data.model.Provider;
import com.iskhak.servicehelper.ui.base.MvpView;

import java.util.List;

public interface ChooseProviderMvpView extends MvpView {
    void showProviders(List<Provider> providers);
    void showProvidersEmpty();
    void showLoadProviderError();
}
