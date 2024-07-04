package com.whereismymotivation.data.local.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContentPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val FEED_NEXT_PAGE_NUMBER = intPreferencesKey("FEED_NEXT_PAGE_NUMBER")
        private val FEED_LAST_SEEN = longPreferencesKey("FEED_LAST_SEEN")
    }

    suspend fun getFeedNextPageNumber() =
        dataStore.data.map { it[FEED_NEXT_PAGE_NUMBER] ?: 1 }.first()

    suspend fun setFeedNextPageNumber(pageNumber: Int) {
        dataStore.edit { it[FEED_NEXT_PAGE_NUMBER] = pageNumber }
    }

    suspend fun getFeedLastSeen() =
        dataStore.data.map { it[FEED_LAST_SEEN] ?: System.currentTimeMillis() }.first()

    suspend fun setFeedLastSeen(time: Long) {
        dataStore.edit { it[FEED_LAST_SEEN] = time }
    }
}