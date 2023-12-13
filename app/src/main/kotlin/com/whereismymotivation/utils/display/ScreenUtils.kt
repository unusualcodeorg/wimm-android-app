package com.whereismymotivation.utils.display

import android.content.res.Resources


object ScreenUtils {

    private const val TAG = "ScreenUtils"

    fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels

    fun getScreenHeight() = Resources.getSystem().displayMetrics.heightPixels
}