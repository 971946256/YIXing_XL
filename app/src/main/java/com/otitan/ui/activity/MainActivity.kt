package com.otitan.ui.activity

import android.Manifest
import android.databinding.Observable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.util.Log
import com.afollestad.materialdialogs.MaterialDialog
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.otitan.TitanApplication
import com.otitan.base.BaseActivity
import com.otitan.location.MyBDLocationListener
import com.otitan.model.CityInfoModel
import com.otitan.model.MonitorModel
import com.otitan.permission.PermissionsActivity
import com.otitan.permission.PermissionsChecker
import com.otitan.ui.adapter.MainAdapter
import com.otitan.ui.mview.IMain
import com.otitan.ui.mview.IMainItem
import com.otitan.ui.viewmodel.MainViewModel
import com.otitan.util.FileUtil
import com.titan.tianqidemo.BR
import com.titan.tianqidemo.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.async
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.uiThread
import java.net.URL
import kotlin.properties.Delegates
import com.titan.tianqidemo.databinding.ActivityMainBinding
import org.jetbrains.anko.toast


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), IMain, IMainItem {

    var adapter: MainAdapter? = null
    var bdLocationListener: MyBDLocationListener by Delegates.notNull()

    // 所需的全部权限
    val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
    private var viewmodel: MainViewModel? = null

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
            initBDLoc()
        }
    }

    override fun initViewObservable() {
        viewModel.isFinishRefreshing.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                binding.refreshLayout.finishRefreshing()
            }
        })
    }

    override fun initData() {
        PermissionsActivity.setPermissionsListener(object : com.otitan.permission.PermissionsActivity.PermissionsListener {
            override fun denide() {

            }

            override fun granted() {
                initBDLoc()
            }
        })
        val url = TitanApplication.sharedPreferences.getString("url", "")
        if (url.isNotBlank()) {
            val drawable = Drawable.createFromPath(url)
            binding.mainLinear.background = drawable
        }
        initBDLoc()

        initAdapter()

        binding.refreshLayout.setEnableLoadmore(false)
        binding.refreshLayout.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                super.onRefresh(refreshLayout)
                viewModel.requestNetWork()
                viewModel.getWeather()
            }
        })
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
        binding.refreshLayout.startRefresh()
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
        val layoutManager = LinearLayoutManager(this, OrientationHelper.VERTICAL, false)
        binding.monitorList.layoutManager = layoutManager
//        binding.monitorList.addItemDecoration(TitanItemDecoration(this,LinearLayoutManager.VERTICAL,10))
        if (adapter == null) {
            adapter = MainAdapter(this, viewModel.initMonitorList(), this, viewModel.selectList)
        } else {
            adapter?.setList(viewModel.initMonitorList())
        }
        Log.e("tag", "adapter:$adapter")
        binding.monitorList.adapter = adapter
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
        bdLocationListener.stop()
        viewModel.isLocation = false
    }

}