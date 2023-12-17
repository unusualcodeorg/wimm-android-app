package com.whereismymotivation.data.local.prefs

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(private val prefs: SharedPreferences) {

    companion object {
        private const val ON_BOARDING_COMPLETED = "PREF_KEY_USER_ON_BOARDING_COMPLETED"
        private const val USER_ID = "PREF_KEY_USER_ID"
        private const val USER_NAME = "PREF_KEY_USER_NAME"
        private const val USER_EMAIL = "PREF_KEY_USER_EMAIL"
        private const val USER_PROFILE_PIC_URL = "USER_PROFILE_PIC_URL"
        private const val ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
        private const val REFRESH_TOKEN = "PREF_KEY_REFRESH_TOKEN"
        private const val DEVICE_ID = "PREF_KEY_DEVICE_ID"
        private const val USER_ROLES = "PREF_KEY_USER_ROLES"
        private const val FIREBASE_TOKEN = "FIREBASE_TOKEN"
        private const val FIREBASE_TOKEN_SENT = "FIREBASE_TOKEN_SENT"
    }

    fun getUserId(): String? =
        prefs.getString(USER_ID, null)

    fun setUserId(userId: String) =
        prefs.edit().putString(USER_ID, userId).apply()

    fun removeUserId() =
        prefs.edit().remove(USER_ID).apply()

    fun getUserName(): String? =
        prefs.getString(USER_NAME, null)

    fun setUserName(userName: String) =
        prefs.edit().putString(USER_NAME, userName).apply()

    fun removeUserName() =
        prefs.edit().remove(USER_NAME).apply()

    fun getUserEmail(): String? =
        prefs.getString(USER_EMAIL, null)

    fun setUserEmail(email: String) =
        prefs.edit().putString(USER_EMAIL, email).apply()

    fun removeUserEmail() =
        prefs.edit().remove(USER_EMAIL).apply()

    fun getUserProfilePicUrlUrl(): String? =
        prefs.getString(USER_PROFILE_PIC_URL, null)

    fun setUserProfileProfilePicUrl(url: String?) =
        prefs.edit().putString(USER_PROFILE_PIC_URL, url).apply()

    fun removeUserProfilePicUrl() =
        prefs.edit().remove(USER_PROFILE_PIC_URL).apply()

    fun getAccessToken(): String? =
        prefs.getString(ACCESS_TOKEN, null)

    fun setAccessToken(token: String) =
        prefs.edit().putString(ACCESS_TOKEN, token).apply()

    fun removeAccessToken() =
        prefs.edit().remove(ACCESS_TOKEN).apply()

    fun getRefreshToken(): String? =
        prefs.getString(REFRESH_TOKEN, null)

    fun setRefreshToken(token: String) =
        prefs.edit().putString(REFRESH_TOKEN, token).apply()

    fun removeRefreshToken() =
        prefs.edit().remove(REFRESH_TOKEN).apply()

    fun getOnBoardingComplete(): Boolean =
        prefs.getBoolean(ON_BOARDING_COMPLETED, false)

    fun setOnBoardingComplete(complete: Boolean) =
        prefs.edit().putBoolean(ON_BOARDING_COMPLETED, complete).apply()

    fun removeOnBoardingComplete() =
        prefs.edit().remove(ON_BOARDING_COMPLETED).apply()

    fun getDeviceId(): String? =
        prefs.getString(DEVICE_ID, null)

    fun setDeviceId(deviceId: String) =
        prefs.edit().putString(DEVICE_ID, deviceId).apply()

    fun setFirebaseToken(token: String) =
        prefs.edit().putString(FIREBASE_TOKEN, token).apply()

    fun getFirebaseToken(): String? =
        prefs.getString(FIREBASE_TOKEN, null)

    fun getFirebaseTokenSent(): Boolean =
        prefs.getBoolean(FIREBASE_TOKEN_SENT, false)

    fun setFirebaseTokenSent() =
        prefs.edit().putBoolean(FIREBASE_TOKEN_SENT, true).apply()

    fun getUserRoles(): String? = prefs.getString(USER_ROLES, null)

    fun setUserRoles(roles: String) = prefs.edit().putString(USER_ROLES, roles).apply()

    fun removeUserRoles() = prefs.edit().remove(USER_ROLES).apply()

}