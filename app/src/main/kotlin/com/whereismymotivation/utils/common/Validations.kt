package com.whereismymotivation.utils.common

import androidx.core.util.PatternsCompat

fun isValidEmail(email: String): Boolean {
    return email.isNotEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
}