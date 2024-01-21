package com.whereismymotivation.ui.navigation

import androidx.annotation.Keep

@Keep
enum class Deeplink(private val url: String) {
    APP("app://wimm"),
    MY_BOX("app://wimm/my-box"),
    CONTENT("app://wimm/content"),
    PROFILE("app://wimm/home/profile");

    fun link(param: String = ""): String =
        if (param.isBlank()) this.url else "${this.url}/$param"

    fun pattern(pattern: String = ""): String =
        if (pattern.isBlank()) this.url else "${this.url}/$pattern"

}