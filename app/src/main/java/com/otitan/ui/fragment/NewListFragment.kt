package com.otitan.ui.fragment

import android.databinding.Observable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.otitan.base.BaseFragment
import com.otitan.ui.adapter.NewListAdapter
import com.otitan.ui.mview.INewList
import com.otitan.ui.viewmodel.NewListViewModel
import com.otitan.util.FileUtil
import com.otitan.util.TitanItemDecoration
import com.titan.tianqidemo.BR
import com.titan.tianqidemo.R
import com.titan.tianqidemo.databinding.FmNewListBinding

class NewListFragment() : BaseFragment<FmNewListBinding, NewListViewModel>(), INewList {
    var viewmodel: NewListViewModel? = null
    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fm_new_list
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initViewModel(): NewListViewModel {
        if (viewmodel == null) {
            viewmodel = NewListViewModel(activity, this)
        }
        return viewmodel as NewListViewModel
    }

    override fun initViewObservable() {
    }

    override fun initData() {
        super.initData()
        setHasOptionsMenu(true)
        (activity!! as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { activity?.finish() }

        viewmodel?.type = arguments?.getInt("type", 1) ?: 1
        val layoutManager = LinearLayoutManager(activity, OrientationHelper.VERTICAL, false)
        binding.rvNewList.layoutManager = layoutManager
        binding.rvNewList.addItemDecoration(TitanItemDecoration(activity!!, 1, 10))
        val list = FileUtil.getZlk(activity!!)
        val adapter = NewListAdapter(activity, list)
        binding.rvNewList.adapter = adapter
    }

    override fun refresh() {
        val adapter = binding.rvNewList.adapter
        if (adapter is NewListAdapter) {
            adapter.items = viewmodel?.data
            adapter.notifyDataSetChanged()
        }
    }
}