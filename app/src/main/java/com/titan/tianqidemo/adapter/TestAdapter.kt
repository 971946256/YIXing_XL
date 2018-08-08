package com.titan.tianqidemo.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.titan.utli.ctx

class TestAdapter(val list: List<String>) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent.ctx))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.text = list[position]
    }

    class ViewHolder(val view: TextView) : RecyclerView.ViewHolder(view)
}