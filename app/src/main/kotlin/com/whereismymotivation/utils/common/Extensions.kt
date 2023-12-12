package com.whereismymotivation.utils.common

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat.getColor

fun TextView.setDrawableColor(@ColorRes color: Int) {
    compoundDrawables.filterNotNull().forEach {
        it.colorFilter = PorterDuffColorFilter(getColor(context, color), PorterDuff.Mode.SRC_IN)
    }
}

fun String.Companion.NULL() = "NULL"