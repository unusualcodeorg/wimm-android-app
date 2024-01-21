package com.whereismymotivation.utils.common

import android.net.Uri
import androidx.core.util.PatternsCompat

fun String.Companion.Null() = "null"
fun String.isValidEmail() =
    this.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidUrl(): Boolean {
    return try {
        val uri = Uri.parse(this)
        uri != null && uri.scheme != null
    } catch (e: Exception) {
        false
    }
}