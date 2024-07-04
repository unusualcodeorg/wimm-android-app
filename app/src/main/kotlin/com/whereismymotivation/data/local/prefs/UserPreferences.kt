package com.whereismymotivation.data.local.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val ON_BOARDING_COMPLETED = booleanPreferencesKey("ON_BOARDING_COMPLETED")
        private val USER_ID = stringPreferencesKey("USER_ID")
        private val USER_NAME = stringPreferencesKey("USER_NAME")
        private val USER_EMAIL = stringPreferencesKey("USER_EMAIL")
        private val USER_PROFILE_PIC_URL = stringPreferencesKey("USER_PROFILE_PIC_URL")
        private val ACCESS_TOKEN = stringPreferencesKey("ACCESS_TOKEN")
        private val REFRESH_TOKEN = stringPreferencesKey("REFRESH_TOKEN")
        private val DEVICE_ID = stringPreferencesKey("DEVICE_ID")
        private val USER_ROLES = stringPreferencesKey("USER_ROLES")
        private val FIREBASE_TOKEN = stringPreferencesKey("FIREBASE_TOKEN")
        private val FIREBASE_TOKEN_SENT = booleanPreferencesKey("FIREBASE_TOKEN_SENT")
    }

    suspend fun getUserId() = dataStore.data.map { it[USER_ID] }.first()

    suspend fun setUserId(userId: String) {
        dataStore.edit { it[USER_ID] = userId }
    }

    suspend fun removeUserId() {
        dataStore.edit { it.remove(USER_ID) }
    }

    suspend fun getUserName() = dataStore.data.map { it[USER_NAME] }.first()

    suspend fun setUserName(userName: String) {
        dataStore.edit { it[USER_NAME] = userName }
    }

    suspend fun removeUserName() {
        dataStore.edit { it.remove(USER_NAME) }
    }

    suspend fun getUserEmail() = dataStore.data.map { it[USER_EMAIL] }.first()

    suspend fun setUserEmail(email: String) {
        dataStore.edit { it[USER_EMAIL] = email }
    }

    suspend fun removeUserEmail() {
        dataStore.edit { it.remove(USER_EMAIL) }
    }

    suspend fun getUserProfilePicUrlUrl() = dataStore.data.map { it[USER_PROFILE_PIC_URL] }.first()

    suspend fun setUserProfileProfilePicUrl(url: String?) {
        url?.let {
            dataStore.edit { it[USER_PROFILE_PIC_URL] = url }
        } ?: removeUserProfilePicUrl()
    }

    suspend fun removeUserProfilePicUrl() {
        dataStore.edit { it.remove(USER_PROFILE_PIC_URL) }
    }

    suspend fun getAccessToken() = dataStore.data.map { it[ACCESS_TOKEN] }.first()

    suspend fun setAccessToken(token: String) {
        dataStore.edit { it[ACCESS_TOKEN] = token }
    }

    suspend fun removeAccessToken() {
        dataStore.edit { it.remove(ACCESS_TOKEN) }
    }

    suspend fun getRefreshToken() = dataStore.data.map { it[REFRESH_TOKEN] }.first()

    suspend fun setRefreshToken(token: String) {
        dataStore.edit { it[REFRESH_TOKEN] = token }
    }

    suspend fun removeRefreshToken() {
        dataStore.edit { it.remove(REFRESH_TOKEN) }
    }

    suspend fun getOnBoardingComplete() =
        dataStore.data.map { it[ON_BOARDING_COMPLETED] }.first() ?: false

    suspend fun setOnBoardingComplete(complete: Boolean) {
        dataStore.edit { it[ON_BOARDING_COMPLETED] = complete }
    }

    suspend fun removeOnBoardingComplete() {
        dataStore.edit { it.remove(ON_BOARDING_COMPLETED) }
    }

    suspend fun getDeviceId() = dataStore.data.map { it[DEVICE_ID] }.first()

    suspend fun setDeviceId(deviceId: String) {
        dataStore.edit { it[DEVICE_ID] = deviceId }
    }

    suspend fun setFirebaseToken(token: String) {
        dataStore.edit { it[FIREBASE_TOKEN] = token }
    }

    suspend fun getFirebaseToken() = dataStore.data.map { it[FIREBASE_TOKEN] }.first()

    suspend fun getFirebaseTokenSent() =
        dataStore.data.map { it[FIREBASE_TOKEN_SENT] }.first() ?: false

    suspend fun setFirebaseTokenSent() {
        dataStore.edit { it[FIREBASE_TOKEN_SENT] = true }
    }

    suspend fun removeFirebaseTokenSent() {
        dataStore.edit { it.remove(FIREBASE_TOKEN_SENT) }
    }

    suspend fun getUserRoles() = dataStore.data.map { it[USER_ROLES] }.first()

    suspend fun setUserRoles(roles: String) {
        dataStore.edit { it[USER_ROLES] = roles }
    }

    suspend fun removeUserRoles() {
        dataStore.edit { it.remove(USER_ROLES) }
    }

}