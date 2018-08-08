package com.titan.data

import android.content.Context
import rx.Observable
import rx.Observer
import rx.schedulers.Schedulers
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by whs on 2017/5/18
 * 后段数据接口实现
 */

class RemoteDataSourceImpl(private val mContext: Context) : RemoteDataSource {


    companion object {
        private var remoteDataSourceImpl: RemoteDataSourceImpl? = null

        fun getInstance(context: Context): RemoteDataSourceImpl {
            if (remoteDataSourceImpl == null) {
                remoteDataSourceImpl = RemoteDataSourceImpl(context)
            }
            return remoteDataSourceImpl as RemoteDataSourceImpl
        }
    }

    override fun getWeather(app: String, weaid: String, appkey: String, sign: String, format: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.getInstance(mContext).server.getWeather(app, weaid, appkey, sign, format)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String>{
                    override fun onError(e: Throwable?) {

                    }

                    override fun onNext(t: String?) {

                    }

                    override fun onCompleted() {

                    }
                })
    }
}
