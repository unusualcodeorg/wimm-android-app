package com.whereismymotivation.data.repository

import com.whereismymotivation.data.local.prefs.AppMetricPreferences
import com.whereismymotivation.utils.common.RatingUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppMetricRepository @Inject constructor(
    private val appMetricPreferences: AppMetricPreferences
) {

    fun isHomeSettingDiscovered(): Boolean =
        appMetricPreferences.isHomeSettingDiscovered()

    fun markHomeSettingDiscovered() =
        appMetricPreferences.markHomeSettingDiscovered()

    fun isProfileSettingDiscovered(): Boolean =
        appMetricPreferences.isProfileSettingDiscovered()

    fun markProfileSettingDiscovered() =
        appMetricPreferences.markProfileSettingDiscovered()

    fun isSearchAndAddMentorsDiscovered(): Boolean =
        appMetricPreferences.isSearchAndAddMentorsDiscovered()

    fun markSearchAndAddMentorsDiscovered() =
        appMetricPreferences.markSearchAndAddMentorsDiscovered()

    fun isSearchAndAddTopicsDiscovered(): Boolean =
        appMetricPreferences.isSearchAndAddTopicsDiscovered()

    fun markSearchAndAddTopicsDiscovered() =
        appMetricPreferences.markSearchAndAddTopicsDiscovered()

    fun isAddOrRemoveMentorDiscovered(): Boolean =
        appMetricPreferences.isAddOrRemoveMentorDiscovered()

    fun markAddOrRemoveMentorDiscovered() =
        appMetricPreferences.markAddOrRemoveMentorDiscovered()

    fun isAddOrRemoveTopicDiscovered(): Boolean =
        appMetricPreferences.isAddOrRemoveTopicDiscovered()

    fun markAddOrRemoveTopicDiscovered() =
        appMetricPreferences.markAddOrRemoveTopicDiscovered()

    /*------------------------------------------------------------------------*/

    fun increaseAppOpenCount() {
        appMetricPreferences.increaseAppOpenCount()
        appMetricPreferences.increaseAppOpenCountInCurrentAppVersion()
    }

    fun getAppOpenCount(): Long =
        appMetricPreferences.getAppOpenCount()

    fun getAppOpenCountInCurrentAppVersion(): Long =
        appMetricPreferences.getAppOpenCountInCurrentAppVersion()


    fun setLastRatingGiven(ratingGiven: Int) {
        appMetricPreferences.setLastRatingGiven(ratingGiven)
        // update lastRatedAppVersion
        appMetricPreferences.setLastRatedAppVersion(getCurrentAppVersion())
    }


    fun getLastRatingGiven(): Int =
        appMetricPreferences.getLastRatingGiven()


    fun setLastRatedAppVersion(lastRatedAppVersion: Long) =
        appMetricPreferences.setLastRatedAppVersion(lastRatedAppVersion)


    fun getLastRatedAppVersion(): Long =
        appMetricPreferences.getLastRatedAppVersion()


    fun setCurrentAppVersion(appVersion: Long) {
        val lastAppVersion = getCurrentAppVersion()
        if (lastAppVersion != appVersion) {
            // app updated or first launch
            resetAppOpenCountInCurrentAppVersion()
            resetRatingShownCountInCurrentAppVersion()
            appMetricPreferences.setCurrentAppVersion(appVersion)
        }
    }


    fun resetAppOpenCountInCurrentAppVersion() =
        appMetricPreferences.resetAppOpenCountInCurrentAppVersion()


    fun getCurrentAppVersion(): Long =
        appMetricPreferences.getCurrentAppVersion()


    fun updateLastRatingShownAppOpenCount() =
        appMetricPreferences.updateLastRatingShownAppOpenCount()


    fun getLastRatingShownAppOpenCount(): Long =
        appMetricPreferences.getLastRatingShownAppOpenCount()


    fun increaseRatingShownCountInCurrentAppVersion() =
        appMetricPreferences.increaseRatingShownCountInCurrentAppVersion()


    fun resetRatingShownCountInCurrentAppVersion() =
        appMetricPreferences.resetRatingShownCountInCurrentAppVersion()


    fun getRatingShownCountInCurrentAppVersion(): Int =
        appMetricPreferences.getRatingShownCountInCurrentAppVersion()


    fun shouldShowRatingDialog(
        isInternetConnected: Boolean,
        maxRating: Int,
        minGapAppOpenShowRatingCount: Int,
        maxShowCountInCurrentVersion: Int
    ): Boolean = RatingUtils.shouldShowRatingDialog(
        isInternetConnected,
        getAppOpenCountInCurrentAppVersion(),
        getLastRatingGiven(),
        getCurrentAppVersion(),
        getLastRatedAppVersion(),
        getLastRatingShownAppOpenCount(),
        getRatingShownCountInCurrentAppVersion(),
        maxRating,
        minGapAppOpenShowRatingCount,
        maxShowCountInCurrentVersion
    )

    fun updateAppRatingControlVariables(isRatingDialogShown: Boolean) {
        if (isRatingDialogShown) {
            updateLastRatingShownAppOpenCount()
            increaseRatingShownCountInCurrentAppVersion()
        }
    }

    fun setDailyMoodRecorderNotificationEnable(enable: Boolean) =
        appMetricPreferences.setDailyMoodRecorderNotificationEnable(enable)

    fun isDailyMoodRecorderNotificationEnabled() =
        appMetricPreferences.getDailyMoodRecorderNotificationEnable()


    /*------------------------------------------------------------------------*/

}
