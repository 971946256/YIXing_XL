package com.otitan.util

import android.content.Context
import android.view.View
import android.widget.TextView

val View.ctx: Context
    get() = context

var TextView.text: CharSequence
    get() = text
    set(text) = setText(text)