package com.otitan.util

import android.widget.Toast
import com.otitan.TitanApplication
import com.otitan.model.CustomerInfo

class BoxStoreUtil {

    companion object{

        private val customerInfo =TitanApplication.boxStore.boxFor(CustomerInfo::class.java)

        //保存用户信息
        @JvmStatic
        fun putCustomerInfo(info: CustomerInfo):Long{
            return customerInfo.put(info)
        }

        //获取全部用户数据
        fun getCustomerInfoAll():List<CustomerInfo>{
            return customerInfo.all
        }

        //根据id删除用户信息
        fun delCustomerInfo(id:Long?):String{
            if (id==null){
                return "信息id为空"
            }
            customerInfo.remove(id)
            return  "删除成功"
        }

        //通过id查询
        fun getInfoById(id:Long?):CustomerInfo{
            if (id==null) return CustomerInfo()
            return customerInfo.get(id)
        }
    }
}