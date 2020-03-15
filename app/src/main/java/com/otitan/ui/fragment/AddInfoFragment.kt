package com.otitan.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import com.afollestad.materialdialogs.MaterialDialog
import com.otitan.base.BaseFragment
import com.otitan.model.CustomerInfo
import com.otitan.ui.mview.IAddInfo
import com.otitan.ui.viewmodel.AddInfoViewModel
import com.otitan.util.BoxStoreUtil
import com.otitan.util.MaterialDialogUtil
import com.otitan.util.Utli
import com.titan.tianqidemo.BR
import com.titan.tianqidemo.R
import com.titan.tianqidemo.databinding.FmAddInfoBinding
import java.io.File
import kotlin.properties.Delegates

class AddInfoFragment() : BaseFragment<FmAddInfoBinding, AddInfoViewModel>(), IAddInfo {

    private var viewmodel: AddInfoViewModel? = null
    var id: Long? = null
    var url: String = "https://mp.weixin.qq.com/s/WPQ449WeN6rXpJNiUaeK-w"

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_add_info
    }

    override fun initViewModel(): AddInfoViewModel {
        if (viewmodel == null) {
            viewmodel = AddInfoViewModel(activity, this)
        }
        return viewmodel as AddInfoViewModel
    }


    override fun initParam() {
        super.initParam()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)
        id = arguments?.getLong("id")

    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { activity?.finish() }


        val info = BoxStoreUtil.getInfoById(id)
        viewmodel?.name?.set(info.name)
        viewmodel?.phone?.set(info.phone)
        viewmodel?.address?.set(info.address)
        viewmodel?.area?.set(info.area)
        viewmodel?.usage?.set(info.usage)
        viewmodel?.yields?.set(info.yields)
        viewmodel?.price19?.set(info.price19)
        viewmodel?.price?.set(info.price)
        viewmodel?.tailMoney?.set(info.tailMoney)
        viewmodel?.remark?.set(info.remark)

    }


    override fun addInfo(customInfo: CustomerInfo) {
//        val path = "${Environment.getExternalStorageDirectory()}/gyyixing"
//        val assestList = activity!!.assets.list("") ?: arrayOf("")
//        val putPath = path + "/" + "弈星用户数据-" + Utli.getTime() + ".xls"
//        if (!File(putPath).exists()) {
//            copyFile(putPath)
//        }
//        for (file in assestList) {
//            if (file == "customer_info.xls") {
//                Utli.writeExcelYX(File(putPath), customInfo)
//                MaterialDialogUtil.showSureDialog(activity!!, "添加成功！", MaterialDialog.SingleButtonCallback { dialog, which ->
//                    viewmodel?.initData()
//                }).show()
//            }
//        }
        if (id == null) {
            val id = BoxStoreUtil.putCustomerInfo(customInfo)
            if (id != 0L) {
                MaterialDialogUtil.showSureDialog(activity!!, "添加成功！", MaterialDialog.SingleButtonCallback { dialog, which ->
                    viewmodel?.initData()
                }).show()
            }
        } else {
            customInfo.id = this.id!!
            val id = BoxStoreUtil.putCustomerInfo(customInfo)
            if (id != 0L) {
                activity?.setResult(1)
                activity?.finish()
            }
        }
    }

    fun copyFile(putPath: String) {
        val assestList = activity!!.assets.list("") ?: arrayOf("")
        if (File(putPath).exists()) return
        for (file in assestList) {
            if (file == "customer_info.xls") {
                Utli.copyFileFromAssets(activity!!, file, putPath)
            }
        }
    }
}