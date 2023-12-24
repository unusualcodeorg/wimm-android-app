package com.whereismymotivation.ui.search

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
enum class SearchMode : Parcelable {
    UNIVERSAL,
    MENTOR,
    TOPIC,
}