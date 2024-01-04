package com.whereismymotivation.utils.common

import androidx.core.util.PatternsCompat

fun String.Companion.Null() = "null"
fun String.isValidEmail() =
    this.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()