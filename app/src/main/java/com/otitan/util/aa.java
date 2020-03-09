package com.otitan.util;

import android.databinding.DataBindingComponent;

public class aa implements android.databinding.DataBindingComponent {

     CustomDataBindingUtil util;

    public  CustomDataBindingUtil getCustomDataBindingUtil() {
        if (util == null) {
            util = new CustomDataBindingUtil();
        }
        return util;
    }
}
