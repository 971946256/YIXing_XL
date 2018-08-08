package com.titan.data

/**
 * Created by whs on 2017/7/10
 */

open class TitanSubscriber<T> : ErrorSubscriber<T>() {
    override fun onCompleted() {

    }

    override fun onError(ex: ExceptionEngine.ApiException) {

    }

    override fun onNext(t: T) {

    }
}


