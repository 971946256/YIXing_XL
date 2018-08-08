package com.titan.data

/**
 * Created by whs on 2017/10/30
 * 数据访问层
 */

class DataRepository private constructor(private val mRemoteDataSource: RemoteDataSourceImpl) : RemoteDataSource {

    companion object {
        private var INSTANCE: DataRepository? = null
        fun getInstance(remoteDataSource: RemoteDataSourceImpl): DataRepository {
            if (INSTANCE == null) {
                INSTANCE = DataRepository(remoteDataSource)
            }
            return INSTANCE as DataRepository
        }
    }

    override fun getWeather(app: String, weaid: String, appkey: String, sign: String, format: String, callback: RemoteDataSource.mCallback) {
        mRemoteDataSource.getWeather(app, weaid, appkey, sign, format, callback)
    }
}
