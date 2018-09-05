package com.otitan.util

class CustomDataBindingComponent : android.databinding.DataBindingComponent {
    var util: CustomDataBindingUtil? = null
    override fun getCustomDataBindingUtil(): CustomDataBindingUtil {
        return if (util == null) {
            CustomDataBindingUtil()
        } else {
            util!!
        }
    }
}