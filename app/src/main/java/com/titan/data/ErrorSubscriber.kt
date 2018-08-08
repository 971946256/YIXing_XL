package com.titan.data

import rx.Observer

/**
 * Created by whs on 2017/7/10
 */

abstract class ErrorSubscriber<T> : Observer<T> {
    override fun onError(e: Throwable) {
        if (e is ExceptionEngine.ApiException) {
            onError(e)
        } else {
            onError(ExceptionEngine.ApiException(e, 123))
        }
    }

    /**
     * 错误回调
     */
    protected abstract fun onError(ex: ExceptionEngine.ApiException)
}
