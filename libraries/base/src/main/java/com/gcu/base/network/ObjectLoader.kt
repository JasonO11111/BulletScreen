package com.gcu.base.network

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

open class ObjectLoader {

    protected fun <T> observe(observable: Observable<T>): Observable<T> {
        return observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}