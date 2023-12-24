package com.whereismymotivation.ui.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class SearchMode(val code: String) : Parcelable {
    UNIVERSAL("universal"),
    MENTOR("mentor"),
    TOPIC("topic")
}