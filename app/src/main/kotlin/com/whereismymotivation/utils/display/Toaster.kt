package com.whereismymotivation.utils.display

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

object Toaster {
    fun show(context: Context, text: CharSequence) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun show(context: Context, @StringRes stringId: Int) {
        Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show()
    }
}