package com.otitan.util

import android.content.Intent
import android.content.res.ColorStateList
import android.databinding.BindingAdapter
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.otitan.base.BindingCommand
import com.otitan.customview.CustomCircleProgressBar
import com.squareup.picasso.Picasso
import com.titan.tianqidemo.R

class CustomDataBindingUtil {
    @BindingAdapter("app:textColor")
    fun setTextColor(textView: TextView, color: ColorStateList) {
        textView.setTextColor(color)
    }


    @BindingAdapter("app:size")
    fun setProgress(progressBar: CustomCircleProgressBar, progress: Int) {
        progressBar.setProgress(progress.toFloat())
    }

    companion object {
        @BindingAdapter("app:image")
        @JvmStatic
        fun setSrc(view: ImageView, resId: Int) {
//            view.setImageURI((Uri.Builder()).scheme("res").path(resId.toString()).build())
            view.setImageResource(resId)
        }

        @BindingAdapter("app:onLongClick")
        @JvmStatic
        fun callPhpne(view: TextView, phone: String) {
            view.setOnLongClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                val uri = Uri.parse("tel:$phone")
                intent.data = uri
                view.context.startActivity(intent)
                true
            }
        }

        @BindingAdapter("app:resource")
        @JvmStatic
        fun loadImage(imageView: ImageView, source: String) {
            Picasso.with(imageView.context).load(source).into(imageView)
//        Glide.with(imageView.context).load(source).into(imageView)
        }

        @BindingAdapter("app:onRefresh", "app:onLoadMore", requireAll = false)
        @JvmStatic
        fun onRefreshAndLoadMore(layout: TwinklingRefreshLayout, refreshCall: BindingCommand, loadMoreCall: BindingCommand) {
            layout.setOnRefreshListener(object : RefreshListenerAdapter() {
                override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                    super.onRefresh(refreshLayout)
                    refreshCall.execute()
                }

                override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                    super.onLoadMore(refreshLayout)
                    loadMoreCall.execute()
                }
            })
        }
    }


}