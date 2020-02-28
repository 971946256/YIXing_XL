package com.otitan.ui.activity

import android.Manifest
import android.content.Context
import android.databinding.Observable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.util.Log
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.google.gson.Gson
import com.otitan.TitanApplication
import com.otitan.base.BaseActivity
import com.otitan.location.MyBDLocationListener
import com.otitan.model.AddressModel
import com.otitan.model.BZModel
import com.otitan.model.CityInfoModel
import com.otitan.model.MonitorModel
import com.otitan.permission.PermissionsActivity
import com.otitan.permission.PermissionsChecker
import com.otitan.ui.adapter.MainSAdapter
import com.otitan.ui.adapter.MainAdapter
import com.otitan.ui.mview.ISMain
import com.otitan.ui.mview.IMain
import com.otitan.ui.mview.IMainItem
import com.otitan.ui.viewmodel.MainViewModel
import com.otitan.util.FileUtil
import com.otitan.util.TitanItemDecoration
import com.otitan.util.Utli
import com.titan.tianqidemo.BR
import com.titan.tianqidemo.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.async
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.uiThread
import java.net.URL
import kotlin.properties.Delegates
import com.titan.tianqidemo.databinding.ActivityMainBinding
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import java.io.File


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), IMain, IMainItem, ISMain {

    var adapter: MainAdapter? = null
    var bdLocationListener: MyBDLocationListener by Delegates.notNull()

    // 所需的全部权限
    val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
    private var viewmodel: MainViewModel? = null

    val addList = ArrayList<AddressModel>()

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(): MainViewModel {
        if (viewmodel == null) {
            viewmodel = MainViewModel(this, this)
        }
        return viewmodel as MainViewModel
    }


    override fun onStart() {
        super.onStart()
        if (PermissionsChecker(this).lacksPermissions(*PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, PermissionsActivity.PERMISSIONS_REQUEST_CODE, *PERMISSIONS)
        } else {
//            initBDLoc()
        }
    }

    override fun initViewObservable() {
        viewModel.isFinishRefreshing.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//                binding.refreshLayout.finishRefreshing()
            }
        })
    }

    override fun initData() {
        PermissionsActivity.setPermissionsListener(object : com.otitan.permission.PermissionsActivity.PermissionsListener {
            override fun denide() {

            }

            override fun granted() {
//                initBDLoc()
            }
        })
        val url = TitanApplication.sharedPreferences.getString("url", "")
        if (url.isNotBlank()) {
            val drawable = Drawable.createFromPath(url)
            binding.mainLinear.background = drawable
        }
//        initBDLoc()

//        initAdapter()

        /*binding.refreshLayout.setEnableLoadmore(false)
        binding.refreshLayout.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                super.onRefresh(refreshLayout)
                viewModel.requestNetWork()
                viewModel.getWeather()
            }
        })*/
    }

    fun initBDLoc() {
        bdLocationListener = MyBDLocationListener.getInstance()
        bdLocationListener.initBDLoc(object : com.otitan.base.ValueCallBack<CityInfoModel> {
            override fun onSuccess(t: CityInfoModel) {
                viewModel.city = t.city
                viewModel.district = t.district
                if (!viewModel.isLocation) {
                    viewModel.getWeather()
                }
            }

            override fun onFail(code: String) {

            }

        })
    }

    override fun startRefresh() {
//        binding.refreshLayout.startRefresh()
    }

    override fun setProgress(progress: Float, title: String) {
        circleProgress.setProgress(progress)
        circleProgress.setTitle(title)
    }

    override fun setImage(url: String) {
        async {
            val path = FileUtil.getImgPath(url, this@MainActivity)
            val drawable = if (path.isBlank()) {
                val savePath = FileUtil.saveImg(url, this@MainActivity)
                if (savePath.isNotBlank()) {
                    TitanApplication.sharedPreferences.edit().putString("url", savePath).apply()
                    Drawable.createFromStream(URL(url).openStream(), "bg.jpg")
                } else {
                    Drawable.createFromPath(savePath)
                }
            } else {
                Drawable.createFromPath(path)
            }
            uiThread {
                binding.mainLinear.background = drawable
            }
        }
    }

    fun initAdapter() {
        /*val layoutManager = LinearLayoutManager(this, OrientationHelper.VERTICAL, false)
        binding.monitorList.layoutManager = layoutManager
//        binding.monitorList.addItemDecoration(TitanItemDecoration(this,LinearLayoutManager.VERTICAL,10))
        if (adapter == null) {
            adapter = MainAdapter(this, viewModel.initMonitorList(), this, viewModel.selectList)
        } else {
            adapter?.setList(viewModel.initMonitorList())
        }
        Log.e("tag", "adapter:$adapter")
        binding.monitorList.adapter = adapter*/
    }

    override fun addAddress() {
        /*var infoValue = binding.editAddress.text.toString()
        if (infoValue.contains("收货人") || infoValue.contains("收货地址") || infoValue.contains("联系电话")) {
            infoValue.replace("收货人", " ")
            infoValue.replace("联系电话", " ")
            infoValue.replace("收货地址", " ")
        }
        infoValue = "$infoValue***"
        val flag = TitanApplication.sharedPreferences.edit().putString(infoValue, "").commit()
        if (flag) {*/
        if (addList.isNotEmpty()) {
            addList.clear()
        }
        val str = FileUtil.getAddress()
        if (str == "") {
            toast("没有联系人信息")
            return
        }

        val bzModel = Gson().fromJson(str, BZModel::class.java)
        //5斤手提礼盒
        viewmodel?.wjst?.set(addInfo(bzModel.wjstlh, "5斤手提礼盒"))
        //30枚中果
        viewmodel?.sszg?.set(addInfo(bzModel.ssmzg, "30枚中果"))
        //30枚大果
        viewmodel?.ssdg?.set(addInfo(bzModel.ssmdg, "30枚大果"))
        //5斤小果
        viewmodel?.wjxg?.set(addInfo(bzModel.wjxg, "5斤小果"))
        //3斤小果
        viewmodel?.sjxg?.set(addInfo(bzModel.sjxg, "3斤小果"))
        //呆小萌礼盒
        viewmodel?.dxm?.set(addInfo(bzModel.dxmlhz, "呆小萌礼盒"))


//            }

        val layoutManager = LinearLayoutManager(this, OrientationHelper.VERTICAL, false)
        binding.rvAddressList.layoutManager = layoutManager
        binding.rvAddressList.addItemDecoration(TitanItemDecoration(this, LinearLayoutManager.VERTICAL, 10))
        val adapter = MainSAdapter(this, addList, this)

        Log.e("tag", "adapter:$adapter")
        binding.rvAddressList.adapter = adapter
//        }
    }

    private fun addInfo(str: String, type: String): String {
        val array = str.split("，")
        var addressModel: AddressModel? = null
        for (index in 1..array.size) {
            when (index % 4) {
                1 -> {
                    addressModel = AddressModel()
                    addressModel.remarks = type
                    addressModel.name = array[index - 1]
                }
                2 -> addressModel?.phone = array[index - 1]
                0 -> {
                    addressModel?.address = array[index - 1]
                    addList.add(addressModel ?: AddressModel())
                }
            }
        }
        return (array.size / 4).toString()
    }

    override fun inputExcel() {
        if (addList.isEmpty()) {
            longToast("请添加用户信息")
            return
        }
        MaterialDialog.Builder(this)
                .title("请选择模板")
                .negativeText("取消")
                .items(R.array.model)
                .itemsCallback { dialog, itemView, position, text ->
                    when (text.toString()) {
                        "通用" -> selectModel(1)
                        "极简" -> selectModel(2)
                    }
                }.show()
    }

    private fun selectModel(type: Int) {
        val path = "${Environment.getExternalStorageDirectory()}/ABC"
        val assestList = this.assets.list("") ?: arrayOf("")
        val putPath = path + "/" + "abc-" + Utli.getTime() + ".xls"
        when (type) {
            1 -> {
                for (file in assestList) {
                    if (file=="tongyong.xls") {
                        Utli.copyFileFromAssets(this, file, putPath)
                        //通用
                        Utli.writeExcel(File(putPath), addList)
                    }
                }
            }
            2 -> {
                for (file in assestList) {
                    if (file=="model.xls") {
                        Utli.copyFileFromAssets(this, file, putPath)
                        //极简
                        Utli.writeExcelSimple(File(putPath), addList)
                    }
                }

            }
        }
        longToast("复制完成")
    }

    override fun selectItem() {
        val path = "${Environment.getExternalStorageDirectory()}/ABC"
        val file = File(path)
        val files = file.listFiles()
        if (files == null) {
            longToast("文件夹为空目录")
            return
        }
        val s = ArrayList<String>()
        for (cFile in files) {
            if (cFile.isFile && cFile.name.endsWith(".csv")) {
                s.add(cFile.name)
            }
        }
        MaterialDialog.Builder(this)
                .title("请选择文件")
                .negativeText("取消")
                .items(s)
                .itemsCallback { dialog, itemView, position, text ->
                    val list = Utli.readCSV(text.toString())
                    val array1 = ArrayList<AddressModel>()
                    val array2 = ArrayList<AddressModel>()
                    val array3 = ArrayList<AddressModel>()
                    val array4 = ArrayList<AddressModel>()
                    val array5 = ArrayList<AddressModel>()
                    val array6 = ArrayList<AddressModel>()
                    addList.clear()
                    for (item in list) {
                        val array = item.split(",")
                        for (i in 1..array[3].toInt()) {
                            if (array[11] != "等待出库") {
                                continue
                            }
                            val addressModel = AddressModel()
                            when (array[1]) {
                                "56163744486" -> {
                                    addressModel.remarks = "5斤手提礼盒"
                                    addressModel.name = array[14]
                                    addressModel.phone = array[16]
                                    addressModel.address = array[15]
                                    array1.add(addressModel)
                                }
                                "56163744484" -> {
                                    addressModel.remarks = "30枚中果"
                                    addressModel.name = array[14]
                                    addressModel.phone = array[16]
                                    addressModel.address = array[15]
                                    array2.add(addressModel)
                                }
                                "56163744485" -> {
                                    addressModel.remarks = "30枚大果"
                                    addressModel.name = array[14]
                                    addressModel.phone = array[16]
                                    addressModel.address = array[15]
                                    array3.add(addressModel)
                                }
                                "58580062290" -> {
                                    addressModel.remarks = "5斤小果"
                                    addressModel.name = array[14]
                                    addressModel.phone = array[16]
                                    addressModel.address = array[15]
                                    array4.add(addressModel)
                                }
                                "58580062289" -> {
                                    addressModel.remarks = "3斤小果"
                                    addressModel.name = array[14]
                                    addressModel.phone = array[16]
                                    addressModel.address = array[15]
                                    array5.add(addressModel)
                                }
                                "56163744487" -> {
                                    addressModel.remarks = "呆小萌礼盒"
                                    addressModel.name = array[14]
                                    addressModel.phone = array[16]
                                    addressModel.address = array[15]
                                    array6.add(addressModel)
                                }
                            }

                        }
                    }
                    addList.addAll(array1)
                    addList.addAll(array2)
                    addList.addAll(array3)
                    addList.addAll(array4)
                    addList.addAll(array5)
                    addList.addAll(array6)
                    //5斤手提礼盒
                    viewmodel?.wjst?.set(array1.size.toString())
                    //30枚中果
                    viewmodel?.sszg?.set(array2.size.toString())
                    //30枚大果
                    viewmodel?.ssdg?.set(array3.size.toString())
                    //5斤小果
                    viewmodel?.wjxg?.set(array4.size.toString())
                    //3斤小果
                    viewmodel?.sjxg?.set(array5.size.toString())
                    //呆小萌礼盒
                    viewmodel?.dxm?.set(array6.size.toString())
                    val layoutManager = LinearLayoutManager(this, OrientationHelper.VERTICAL, false)
                    binding.rvAddressList.layoutManager = layoutManager
                    binding.rvAddressList.addItemDecoration(TitanItemDecoration(this, LinearLayoutManager.VERTICAL, 10))
                    val adapter = MainSAdapter(this, addList, this)

                    Log.e("tag", "adapter:$adapter")
                    binding.rvAddressList.adapter = adapter
                }.show()

    }

    override fun setValue() {

    }

    override fun setList(itemList: List<MonitorModel>) {
        adapter?.setList(itemList)
    }

    override fun showSiteChooseDialgo() {
        val dialog = MaterialDialog.Builder(this).title("观测站点")
                .items(R.array.site_array)
                .itemsCallback { dialog, itemView, position, text ->
                    viewModel.site.set(text.toString())
                    viewModel.initMonitorList()
                    initAdapter()
                    dialog.dismiss()
                }
        dialog.show()
    }

    override fun setType(type: String) {
        viewModel.type = type
    }

    override fun setSelect(position: Int) {
        viewModel.selectList.forEachWithIndex { i, b ->
            viewModel.selectList[i] = position == i
        }
        adapter?.setData(viewModel.selectList)
    }

    override fun startActivity() {

    }

    override fun onPause() {
        super.onPause()
//        bdLocationListener.stop()
        viewModel.isLocation = false
    }

}