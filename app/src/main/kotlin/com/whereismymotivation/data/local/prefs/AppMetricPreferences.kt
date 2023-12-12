package com.whereismymotivation.data.local.prefs

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppMetricPreferences @Inject constructor(private val prefs: SharedPreferences) {

    companion object {
        private const val HOME_SETTINGS_DISCOVERED = "PREF_KEY_HOME_SETTINGS_DISCOVERED"
        private const val PROFILE_SETTINGS_DISCOVERED = "PREF_KEY_PROFILE_SETTINGS_DISCOVERED"
        private const val SEARCH_AND_ADD_MENTORS_DISCOVERED = "PREF_KEY_SEARCH_ADD_ADD_MENTORS_DISCOVERED"
        private const val SEARCH_AND_ADD_TOPICS_DISCOVERED = "PREF_KEY_SEARCH_AND_ADD_TOPICS_DISCOVERED"
        private const val ADD_OR_REMOVE_MENTOR_DISCOVERED = "PREF_KEY_ADD_OR_REMOVE_MENTOR_DISCOVERED"
        private const val ADD_OR_REMOVE_TOPIC_DISCOVERED = "PREF_KEY_ADD_OR_REMOVE_TOPIC_DISCOVERED"

        private const val APP_OPEN_COUNT = "APP_OPEN_COUNT"
        private const val APP_OPEN_COUNT_IN_CURRENT_VERSION = "APP_OPEN_COUNT_IN_CURRENT_VERSION"
        private const val LAST_RATING_GIVEN = "PREF_KEY_LAST_RATING_GIVEN"
        private const val LAST_RATED_APP_VERSION = "PREF_KEY_LAST_RATED_APP_VERSION"
        private const val CURRENT_APP_VERSION = "PREF_KEY_CURRENT_APP_VERSION"
        private const val LAST_RATING_SHOWN_APP_OPEN_COUNT = "PREF_KEY_LAST_RATING_SHOWN_APP_OPEN_COUNT"
        private const val NUM_TIMES_RATING_SHOWN_IN_CURRENT_APP_VERSION =
            "PREF_KEY_NUM_TIMES_RATING_SHOWN_IN_CURRENT_APP_VERSION"

        private const val DAILY_MOOD_RECORDER_NOTIFICATION = "PREF_KEY_DAILY_MOOD_RECORDER_NOTIFICATION"

    }
    fun isHomeSettingDiscovered(): Boolean =
        prefs.getBoolean(HOME_SETTINGS_DISCOVERED, false)

    fun markHomeSettingDiscovered() =
        prefs.edit().putBoolean(HOME_SETTINGS_DISCOVERED, true).apply()

    fun isProfileSettingDiscovered(): Boolean =
        prefs.getBoolean(PROFILE_SETTINGS_DISCOVERED, false)

    fun markProfileSettingDiscovered() =
        prefs.edit().putBoolean(PROFILE_SETTINGS_DISCOVERED, true).apply()

    fun isSearchAndAddMentorsDiscovered(): Boolean =
        prefs.getBoolean(SEARCH_AND_ADD_MENTORS_DISCOVERED, false)

    fun markSearchAndAddMentorsDiscovered() =
        prefs.edit().putBoolean(SEARCH_AND_ADD_MENTORS_DISCOVERED, true).apply()

    fun isSearchAndAddTopicsDiscovered(): Boolean =
        prefs.getBoolean(SEARCH_AND_ADD_TOPICS_DISCOVERED, false)

    fun markSearchAndAddTopicsDiscovered() =
        prefs.edit().putBoolean(SEARCH_AND_ADD_TOPICS_DISCOVERED, true).apply()

    fun isAddOrRemoveMentorDiscovered(): Boolean =
        prefs.getBoolean(ADD_OR_REMOVE_MENTOR_DISCOVERED, false)

    fun markAddOrRemoveMentorDiscovered() =
        prefs.edit().putBoolean(ADD_OR_REMOVE_MENTOR_DISCOVERED, true).apply()

    fun isAddOrRemoveTopicDiscovered(): Boolean =
        prefs.getBoolean(ADD_OR_REMOVE_TOPIC_DISCOVERED, false)

    fun markAddOrRemoveTopicDiscovered() =
        prefs.edit().putBoolean(ADD_OR_REMOVE_TOPIC_DISCOVERED, true).apply()

    /*------------------------------------------------------------------------*/

    fun increaseAppOpenCount() =
        prefs.edit().putLong(APP_OPEN_COUNT, getAppOpenCount() + 1).apply()


    fun getAppOpenCount(): Long =
        prefs.getLong(APP_OPEN_COUNT, 0)


    fun increaseAppOpenCountInCurrentAppVersion() =
        prefs.edit().putLong(APP_OPEN_COUNT_IN_CURRENT_VERSION, getAppOpenCountInCurrentAppVersion() + 1).apply()

    fun getAppOpenCountInCurrentAppVersion(): Long =
        prefs.getLong(APP_OPEN_COUNT_IN_CURRENT_VERSION, 0)


    fun resetAppOpenCountInCurrentAppVersion() =
        prefs.edit().putLong(APP_OPEN_COUNT_IN_CURRENT_VERSION, 0).apply()

    fun setLastRatingGiven(ratingGiven: Int) =
        prefs.edit().putInt(LAST_RATING_GIVEN, ratingGiven).apply()

    fun getLastRatingGiven(): Int =
        prefs.getInt(LAST_RATING_GIVEN, 0)

    fun setLastRatedAppVersion(lastRatedAppVersion: Int) =
        prefs.edit().putInt(LAST_RATED_APP_VERSION, lastRatedAppVersion).apply()

    fun getLastRatedAppVersion(): Int =
        prefs.getInt(LAST_RATED_APP_VERSION, 0)

    fun setCurrentAppVersion(appVersion: Long) =
        prefs.edit().putLong(CURRENT_APP_VERSION, appVersion).apply()

    fun getCurrentAppVersion(): Long =
        prefs.getLong(CURRENT_APP_VERSION, 0)

    fun updateLastRatingShownAppOpenCount() =
        prefs.edit().putLong(LAST_RATING_SHOWN_APP_OPEN_COUNT, getAppOpenCountInCurrentAppVersion()).apply()

    fun getLastRatingShownAppOpenCount(): Long =
        prefs.getLong(LAST_RATING_SHOWN_APP_OPEN_COUNT, 0)


    fun increaseRatingShownCountInCurrentAppVersion() {
        prefs.edit().putInt(
            NUM_TIMES_RATING_SHOWN_IN_CURRENT_APP_VERSION,
            getRatingShownCountInCurrentAppVersion() + 1
        ).apply()
    }

    fun resetRatingShownCountInCurrentAppVersion() =
        prefs.edit().putInt(NUM_TIMES_RATING_SHOWN_IN_CURRENT_APP_VERSION, 0).apply()

    fun getRatingShownCountInCurrentAppVersion(): Int =
        prefs.getInt(NUM_TIMES_RATING_SHOWN_IN_CURRENT_APP_VERSION, 0)

    fun setDailyMoodRecorderNotificationEnable(enable: Boolean) =
        prefs.edit().putBoolean(DAILY_MOOD_RECORDER_NOTIFICATION, enable).apply()

    fun getDailyMoodRecorderNotificationEnable() =
        prefs.getBoolean(DAILY_MOOD_RECORDER_NOTIFICATION, true)

    /*------------------------------------------------------------------------*/
}