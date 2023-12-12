package com.whereismymotivation.data.local.prefs

import android.content.SharedPreferences
import javax.inject.Inject

class ContentPreferences @Inject constructor(private val prefs: SharedPreferences) {

    companion object {
        private const val FEED_NEXT_PAGE_NUMBER = "FEED_NEXT_PAGE_NUMBER"
        private const val FEED_LAST_SEEN = "FEED_LAST_SEEN"
    }

    fun getFeedNextPageNumber(): Int =
        prefs.getInt(FEED_NEXT_PAGE_NUMBER, 1)

    fun setFeedNextPageNumber(pageNumber: Int) =
        prefs.edit().putInt(FEED_NEXT_PAGE_NUMBER, pageNumber).apply()

    fun getFeedLastSeen(): Long =
        prefs.getLong(FEED_LAST_SEEN, System.currentTimeMillis())

    fun setFeedLastSeen(time: Long) =
        prefs.edit().putLong(FEED_LAST_SEEN, time).apply()
}