package com.iskhak.serviceprovider.helpers;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

public class RxObservableList<T> {
    protected final List<T> list;
    protected final PublishSubject<T> onAdd;

    public RxObservableList() {
        this.list = new ArrayList<T>();
        this.onAdd = PublishSubject.create();
    }
    public void add(T value) {
        list.add(value);
        onAdd.onNext(value);
    }
    public Observable<T> getObservable() {
        return onAdd;
    }
}