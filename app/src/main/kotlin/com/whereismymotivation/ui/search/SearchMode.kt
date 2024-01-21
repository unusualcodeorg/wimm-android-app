package com.whereismymotivation.ui.search

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
enum class SearchMode : Parcelable {
    UNIVERSAL,
    MENTOR,
    TOPIC;

    companion object {
        fun fromName(name: String): SearchMode =
            try {
                SearchMode.valueOf(name)
            } catch (e: IllegalArgumentException) {
                UNIVERSAL
            }
    }
}