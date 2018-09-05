package com.otitan.data.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.otitan.model.*
import rx.Observer
import rx.schedulers.Schedulers
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by hanyw on 2018/8/18
 * 后段数据接口实现
 */

class RemoteDataSourceImpl() : RemoteDataSource {

    private object Holder {
        val single = RemoteDataSourceImpl()
    }

    companion object {
        val instance: RemoteDataSourceImpl by lazy { Holder.single }
//        private var remoteDataSourceImpl: RemoteDataSourceImpl by Delegates.notNull()
//        fun getInstance(): RemoteDataSourceImpl {
//            remoteDataSourceImpl = RemoteDataSourceImpl()
////            if (remoteDataSourceImpl == null) {
////                remoteDataSourceImpl = RemoteDataSourceImpl(context)
////            }
//            return remoteDataSourceImpl as RemoteDataSourceImpl
//        }
    }

    override fun getWeather(app: String, weaid: String, appkey: String, sign: String, format: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.getWeather(app, weaid, appkey, sign, format)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
                    override fun onError(e: Throwable?) {

                    }

                    override fun onNext(t: String?) {

                    }

                    override fun onCompleted() {

                    }
                })
    }

    /**
     * 林内数据
     */
    override fun getZDQXZLNData(site: String, type: String, stime: String, etime: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.getZDQXZLNData(site, type, stime, etime)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(HttpResponseFunc<String>())
                .subscribe(object : Observer<String> {
                    override fun onError(e: Throwable?) {
                        if (e?.message != null) {
                            callback.onFailure(e.message!!)
                        } else {
                            callback.onFailure(e.toString())
                        }
                    }

                    override fun onNext(t: String?) {
                        t?.let {
                            val gson = Gson()
                            val resultType2 = object : TypeToken<ResultModel<Any>>() {}.type
                            val resultModel2 = gson.fromJson<ResultModel<Any>>(t.trim(), resultType2)
                            if (resultModel2.key == "0") {
                                val resultType1 = object : TypeToken<ResultModel<List<ZDQXZLNDataModel>>>() {}.type
                                val resultModel1 = gson.fromJson<ResultModel<List<ZDQXZLNDataModel>>>(t.trim(), resultType1)
                                callback.onSuccess(resultModel1.content!!)
                            } else {
                                callback.onFailure(resultModel2.content.toString())
                            }
                            return
                        }
                        callback.onFailure("网络错误")
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 林外数据
     */
    override fun getZDQXZLWData(site: String, type: String, stime: String, etime: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.getZDQXZLWData(site, type, stime, etime)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(HttpResponseFunc<String>())
                .subscribe(object : Observer<String> {
                    override fun onError(e: Throwable?) {
                        if (e?.message != null) {
                            callback.onFailure(e.message!!)
                        } else {
                            callback.onFailure(e.toString())
                        }
                    }

                    override fun onNext(t: String?) {
                        setContentData(t, callback)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 空气质量 pm2.5
     */
    override fun getKQZLData(site: String, stime: String, etime: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.getKQZLData(site, stime, etime)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(HttpResponseFunc<String>())
                .subscribe(object : Observer<String> {
                    override fun onError(e: Throwable?) {
                        if (e?.message != null) {
                            callback.onFailure(e.message!!)
                        } else {
                            callback.onFailure(e.toString())
                        }
                    }

                    override fun onNext(t: String?) {
                        setContentData(t, callback)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 负氧离子
     */
    override fun getFYLZData(site: String, stime: String, etime: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.getFYLZData(site, stime, etime)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(HttpResponseFunc<String>())
                .subscribe(object : Observer<String> {
                    override fun onError(e: Throwable?) {
                        if (e?.message != null) {
                            callback.onFailure(e.message!!)
                        } else {
                            callback.onFailure(e.toString())
                        }
                    }

                    override fun onNext(t: String?) {
                        setContentData(t, callback)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 雨滴谱仪
     */
    override fun getYDPYData(site: String, type: String, stime: String, etime: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.getYDPYData(site, type, stime, etime)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(HttpResponseFunc<String>())
                .subscribe(object : Observer<String> {
                    override fun onError(e: Throwable?) {
                        if (e?.message != null) {
                            callback.onFailure(e.message!!)
                        } else {
                            callback.onFailure(e.toString())
                        }
                    }

                    override fun onNext(t: String?) {
                        setContentData(t, callback)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 土壤水势
     */
    override fun getTRSSData(site: String, type: String, stime: String, etime: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.getTRSSData(site, type, stime, etime)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(HttpResponseFunc<String>())
                .subscribe(object : Observer<String> {
                    override fun onError(e: Throwable?) {
                        if (e?.message != null) {
                            callback.onFailure(e.message!!)
                        } else {
                            callback.onFailure(e.toString())
                        }
                    }

                    override fun onNext(t: String?) {
                        setContentData(t, callback)
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 树干液流
     */
    override fun getStemFlowData(site: String, stime: String, etime: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.getStemFlowData(site, stime, etime)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(HttpResponseFunc<String>())
                .subscribe(object : Observer<String> {
                    override fun onError(e: Throwable?) {
                        if (e?.message != null) {
                            callback.onFailure(e.message!!)
                        } else {
                            callback.onFailure(e.toString())
                        }
                    }

                    override fun onNext(t: String?) {
                        t?.let {
                            val gson = Gson()
                            val resultType1 = object : TypeToken<ResultModel<Any>>() {}.type
                            val resultModel1 = gson.fromJson<ResultModel<Any>>(t.trim(), resultType1)
                            if (resultModel1.key == "0") {
                                val resultType2 = object : TypeToken<ResultModel<List<SGYLDataModel>>>() {}.type
                                val resultModel2 = gson.fromJson<ResultModel<List<SGYLDataModel>>>(t.trim(), resultType2)
                                callback.onSuccess(resultModel2.content!!)
                            } else {
                                callback.onFailure(resultModel1.content.toString())
                            }
                            return
                        }
                        callback.onFailure("网络错误")
                    }

                    override fun onCompleted() {
                    }
                })
    }

    /**
     * 获取所有最新数据
     */
    override fun getAllNewestData(callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.server.getAllNewestData()
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(HttpResponseFunc<String>())
                .subscribe(object : Observer<String> {
                    override fun onError(e: Throwable?) {
                        if (e?.message != null) {
                            callback.onFailure(e.message!!)
                        } else {
                            callback.onFailure(e.toString())
                        }
                    }

                    override fun onNext(t: String?) {
                        t?.let {
                            val gson = Gson()
                            val resultType = object : TypeToken<AllNewDataModel>() {}.type
                            val resultModel = gson.fromJson<AllNewDataModel>(t.trim(), resultType)
                            callback.onSuccess(resultModel)
                            return
                        }
                        callback.onFailure("网络错误")
                    }

                    override fun onCompleted() {

                    }
                })

    }

    override fun getWeather(citykey: String, callback: RemoteDataSource.mCallback) {
        val observable = RetrofitHelper.instance.wServer.getWeather(citykey)
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(HttpResponseFunc<AllForecastModel>())
                .subscribe(object :Observer<AllForecastModel>{
                    override fun onError(e: Throwable?) {
                        if (e?.message != null) {
                            callback.onFailure(e.message!!)
                        } else {
                            callback.onFailure(e.toString())
                        }
                    }

                    override fun onNext(t: AllForecastModel) {
                        callback.onSuccess(t)
                    }

                    override fun onCompleted() {
                    }
                })
    }
    fun setContentData(t: String?, callback: RemoteDataSource.mCallback) {
        t?.let {
            val gson = Gson()
            val resultType1 = object : TypeToken<ResultModel<Any>>() {}.type
            val resultModel1 = gson.fromJson<ResultModel<Any>>(t.trim(), resultType1)
            if (resultModel1.key == "0") {
                val resultType2 = object : TypeToken<ResultModel<List<ContentDataModel>>>() {}.type
                val resultModel2 = gson.fromJson<ResultModel<List<ContentDataModel>>>(t.trim(), resultType2)
                callback.onSuccess(resultModel2.content!!)
            } else {
                callback.onFailure(resultModel1.content.toString())
            }
            return
        }
        callback.onFailure("网络错误")
    }
}
