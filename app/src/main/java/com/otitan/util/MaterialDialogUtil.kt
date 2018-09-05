package com.otitan.util

import android.app.Dialog
import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog


/**
 * Created by hanyw on 2017/11/10/010.
 * 进度弹窗
 */

class MaterialDialogUtil private constructor() {

    var progressDialog: MaterialDialog? = null

    companion object {

        /**
         * 操作信息提示弹窗
         *
         * @param context
         * @param msg
         * @return
         */
        @JvmStatic
        fun showSureDialog(context: Context, msg: String, callback: MaterialDialog.SingleButtonCallback): MaterialDialog {
            return MaterialDialog.Builder(context)
                    .title("提示")
                    .positiveText("确定")
                    .negativeText("取消")
                    .content(msg)
                    .onNegative { dialog, which -> dialog.dismiss() }
                    .onPositive(callback)
                    .build()
        }

        /**
         * 加载数据进度弹窗
         *
         * @param context
         * @param msg
         * @return
         */
        @JvmStatic
        fun showLoadProgress(context: Context, msg: String, isCancel: Boolean): Dialog {
            if (getInstance().progressDialog?.context != context) {
                getInstance().progressDialog = null
            }
            if (getInstance().progressDialog == null) {
                getInstance().progressDialog = MaterialDialog.Builder(context)
                        .content(msg)
                        .progress(true, 0)
                        .cancelable(isCancel)
                        .canceledOnTouchOutside(isCancel)
                        .build()
            }
            return getInstance().progressDialog!!
        }

        @JvmStatic
        fun showLoadProgress(context: Context, msg: String): MaterialDialog {
            return MaterialDialog.Builder(context)
                    .content(msg)
                    .progress(true, 0)
                    .cancelable(true)
                    .canceledOnTouchOutside(false)
                    .build()
        }


        //提示信息dialog
        @JvmStatic
        fun showPromptDialog(context: Context, msg: String): MaterialDialog.Builder {
            return MaterialDialog.Builder(context)
                    .positiveText("确定")
                    .content(msg)
                    .onNegative { dialog, which -> dialog.dismiss() }
        }

        fun getInstance() = Holder.instance
    }

    private object Holder {
        val instance = MaterialDialogUtil()
    }
}
