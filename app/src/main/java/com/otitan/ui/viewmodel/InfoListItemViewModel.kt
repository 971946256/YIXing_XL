package com.otitan.ui.viewmodel

import android.content.*
import android.databinding.ObservableField
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.annotation.NonNull
import android.util.Log
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.otitan.base.BaseViewModel
import com.otitan.base.ContainerActivity
import com.otitan.model.CustomerInfo
import com.otitan.ui.fragment.AddInfoFragment
import com.otitan.ui.fragment.InfoListFragment
import com.otitan.util.BoxStoreUtil
import com.otitan.util.MaterialDialogUtil
import com.titan.tianqidemo.R
import org.jetbrains.anko.toast
import android.support.v4.content.ContextCompat.startActivity
import com.otitan.model.CustomerInfo_.phone
import android.content.Intent


/**
 * 用户信息item
 */
class InfoListItemViewModel() : BaseViewModel() {

    val model = ObservableField<CustomerInfo>()

    constructor(context: Context?, fm: InfoListFragment?) : this() {
        mContext = context
        fragment = fm
    }

    fun onClick() {
        val list = ArrayList<String>()
        list.add("姓名：" + model.get()?.name)
        list.add("电话：" + model.get()?.phone)
        list.add("地址：" + model.get()?.address)
        list.add("基地面积：" + model.get()?.area + "  亩")
        list.add("预计总用粉量：" + model.get()?.usage + "  g")
        list.add("基地产量：" + model.get()?.yields + "  斤/亩")
        list.add("备注：" + model.get()?.remark)
        list.add("添加时间：" + model.get()?.time)
        MaterialDialog.Builder(mContext!!)
                .positiveText("编辑")
                .negativeText("删除")
                .neutralText("取消")
                .title("详细信息")
                .items(list)
                .onPositive() { dialog, which ->
                    val bundle = Bundle()
                    bundle.putLong("id", model.get()?.id ?: 0)
                    Log.e("tag", "id:${model.get()?.id}")
//                    startContainerActivity(AddInfoFragment::class.java.canonicalName, bundle)
                    val intent = Intent(mContext, ContainerActivity::class.java)
                    intent.putExtra("fragment", AddInfoFragment::class.java.canonicalName)
                    intent.putExtra("bundle", bundle)
                    fragment?.startActivityForResult(intent, 1)
                }
                .onNegative() { dialog, which ->
                    MaterialDialogUtil.showSureDialog(mContext!!, "是否确定删除？", MaterialDialog.SingleButtonCallback { dialog2, which2 ->
                        mContext?.toast(BoxStoreUtil.delCustomerInfo(model.get()?.id))
                        (fragment as InfoListFragment).refresh()
                        dialog2.dismiss()
                    }).show()
                }.onNeutral() { dialog, which ->
                    dialog.dismiss()
                }.build().show()
    }

    fun phoneMenu() {
        if (model.get()?.phone.isNullOrEmpty()) {
            mContext?.toast("号码错误")
            return
        }
        MaterialDialog.Builder(mContext!!).items(R.array.phone_menu).itemsCallback { dialog, itemView, position, text ->
            when (text) {
                "复制" -> {
                    val clipboardManager = mContext?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, model.get()?.phone))
                    mContext?.toast("复制成功")
                }
                "呼叫" -> {
                    val uri = Uri.parse("tel:" + model.get()?.phone)
                    val intent = Intent(Intent.ACTION_DIAL, uri)
                    mContext?.startActivity(intent)
                }
                "发短信" -> {
                    val uri = Uri.parse("smsto:${model.get()?.phone}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    mContext?.startActivity(intent)
                }
                "新增联系人" -> {
                    val insertUri = ContactsContract.Contacts.CONTENT_URI
                    val intent = Intent(Intent.ACTION_INSERT, insertUri)
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, model.get()?.name)//名字显示在名字框
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, model.get()?.phone)//号码显示在号码框
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, model.get()?.address)//地址显示在地址框
                    mContext?.startActivity(intent)
                }
                "保存到已有联系人" -> {
                    savePhone()
                }
            }
            dialog.dismiss()
        }.show()

    }

    //保存到联系人
    fun savePhone() {
        val intent = Intent(Intent.ACTION_INSERT_OR_EDIT)
        intent.type = "vnd.android.cursor.item/person"
        intent.type = "vnd.android.cursor.item/contact"
        intent.type = "vnd.android.cursor.item/raw_contact"
        intent.putExtra(ContactsContract.Intents.Insert.NAME, model.get()?.name)
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, model.get()?.phone)
        intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, 3)
        mContext?.startActivity(intent)

        /*下面代码可直接写入通讯录
        val values = ContentValues()
        // 向RawContacts.CONTENT_URI空值插入，
        // 先获取Android系统返回的rawContactId
        // 后面要基于此id插入值
        val rawContactUri = mContext?.contentResolver?.insert(ContactsContract.RawContacts.CONTENT_URI, values)
        val rawContactId = ContentUris.parseId(rawContactUri)
        values.clear()

        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
        // 内容类型
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
        // 联系人名
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, model.get()?.name)
        // 向联系人URI添加联系人名
        mContext?.contentResolver?.insert(ContactsContract.Data.CONTENT_URI, values)
        values.clear()

        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
        // 联系人的电话号码
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, model.get()?.phone)
        // 电话类型
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
        // 向联系人电话号码URI添加电话号码
        mContext?.contentResolver?.insert(ContactsContract.Data.CONTENT_URI, values)
        values.clear()*/

    }

}