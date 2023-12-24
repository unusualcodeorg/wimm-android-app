package com.whereismymotivation.ui.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class SearchMode(val code: String) : Parcelable {
    SEARCH_MODE_UNIVERSAL("SEARCH_MODE_UNIVERSAL"),
    SEARCH_MODE_MENTOR("SEARCH_MODE_MENTOR"),
    SEARCH_MODE_TOPIC("SEARCH_MODE_TOPIC")
}