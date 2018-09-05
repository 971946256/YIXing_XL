package com.otitan.data

import com.otitan.data.local.LocalDataSourceImpl
import com.otitan.data.remote.RemoteDataSource
import com.otitan.data.remote.RemoteDataSourceImpl

/**
 * Created by hanyw on 2018/8/10
 * 数据访问层
 */

class DataRepository private constructor(private val localDataSource: LocalDataSourceImpl, private val mRemoteDataSource: RemoteDataSourceImpl) : RemoteDataSource {

    companion object {
        @Volatile
        private var INSTANCE: DataRepository? = null

        fun getInstance(localDataSource: LocalDataSourceImpl, remoteDataSource: RemoteDataSourceImpl): DataRepository {
            if (INSTANCE == null) {
                synchronized(DataRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = DataRepository(localDataSource, remoteDataSource)
                    }
                }
            }
            return INSTANCE!!
        }
    }

    override fun getWeather(app: String, weaid: String, appkey: String, sign: String, format: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.getWeather(app, weaid, appkey, sign, format, callback)
    }

    override fun getZDQXZLNData(site: String, type: String, stime: String, etime: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.getZDQXZLNData(site, type, stime, etime, callback)
    }

    override fun getZDQXZLWData(site: String, type: String, stime: String, etime: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.getZDQXZLWData(site, type, stime, etime, callback)
    }

    override fun getKQZLData(site: String, stime: String, etime: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.getKQZLData(site, stime, etime, callback)
    }

    override fun getFYLZData(site: String, stime: String, etime: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.getFYLZData(site, stime, etime, callback)
    }

    override fun getYDPYData(site: String, type: String, stime: String, etime: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.getYDPYData(site, type, stime, etime, callback)
    }

    override fun getTRSSData(site: String, type: String, stime: String, etime: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.getTRSSData(site, type, stime, etime, callback)
    }

    override fun getStemFlowData(site: String, stime: String, etime: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.getStemFlowData(site, stime, etime, callback)
    }

    override fun getAllNewestData(callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.getAllNewestData(callback)
    }

    override fun getWeather(citykey: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.getWeather(citykey, callback)
    }
}
