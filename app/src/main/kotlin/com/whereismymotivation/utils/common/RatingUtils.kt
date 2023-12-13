package com.whereismymotivation.utils.common

object RatingUtils {

    fun shouldShowRatingDialog(
        isInternetConnected: Boolean,
        appOpenCountInCurrentAppVersion: Long,
        lastRatingGiven: Int,
        currentAppVersion: Long,
        lastRatedAppVersion: Long,
        lastRatingShownAppOpenCount: Long,
        ratingShownCountInCurrentAppVersion: Int,
        maxRating: Int,
        minGapAppOpenShowRatingCount: Int,
        maxShowCountInCurrentVersion: Int
    ): Boolean {

        // Do not simplify or optimize anything as this has been done for readability

        // Do not show rating if internet is not connected,
        // as the user can not rate us on playStore
        if (!isInternetConnected) {
            return false
        }

        // we can also add check if user gave max star but did not went to playStore
        // Do not show as user has already rated 5 (there may be case use has not rated on playStore
        // Not over-killing as we do not know user has rated on playStore or not)
        if (lastRatingGiven == maxRating) {
            return false
        }

        // Do not show rating dialog in the same app version if user has already rated anything
        // more than 0 (zero means not rated)
        if (lastRatedAppVersion == currentAppVersion && lastRatingGiven > 0) {
            return false
        }

        // Do not show more than a max threshold times in the current app version
        if (ratingShownCountInCurrentAppVersion >= maxShowCountInCurrentVersion) {
            return false
        }

        // Show only after a min gap of app open count, it also means that first rating will be shown on condition
        // when app open count >= 5 with internet connected.
        if (lastRatingShownAppOpenCount + minGapAppOpenShowRatingCount <= appOpenCountInCurrentAppVersion) {
            return true
        }

        return false
    }
}// This class is not publicly instantiable
