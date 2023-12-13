package com.whereismymotivation.data.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.whereismymotivation.data.local.prefs.UserPreferences
import com.whereismymotivation.data.model.AppUser
import com.whereismymotivation.data.model.Role
import com.whereismymotivation.data.remote.NetworkService
import com.whereismymotivation.data.remote.request.FirebaseTokenRequest
import com.whereismymotivation.data.remote.request.MessageRequest
import com.whereismymotivation.utils.log.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val networkService: NetworkService,
    private val userPreferences: UserPreferences
) {

    fun saveCurrentUser(user: AppUser) {
        removeCurrentUser()
        userPreferences.setUserId(user.userId)
        user.userName?.let { userPreferences.setUserName(it) }
        user.userEmail?.let { userPreferences.setUserEmail(it) }

        val roles: String =
            try {
                Moshi.Builder().build().adapter<List<Role>>(
                    Types.newParameterizedType(
                        List::class.java,
                        Role::class.java
                    )
                ).toJson(user.userRoles)
            } catch (e: AssertionError) {
                Logger.record(e)
                "[]"
            }

        userPreferences.setUserRoles(roles)
        userPreferences.setAccessToken(user.accessToken)
        userPreferences.setRefreshToken(user.refreshToken)
        userPreferences.setUserFacebookProfilePicUrl(user.facebookProfilePicUrl)
        userPreferences.setUserGoogleProfilePicUrl(user.googleProfilePicUrl)
    }

    fun removeCurrentUser() {
        userPreferences.removeUserId()
        userPreferences.removeUserName()
        userPreferences.removeUserEmail()
        userPreferences.removeAccessToken()
        userPreferences.removeRefreshToken()
        userPreferences.removeUserFacebookProfilePicUrl()
        userPreferences.removeUserGoogleProfilePicUrl()
        userPreferences.removeOnBoardingComplete()
        userPreferences.removeUserRoles()
    }

    fun getCurrentUser(): AppUser? {

        val userId = userPreferences.getUserId()
        val userName = userPreferences.getUserName()
        val userEmail = userPreferences.getUserEmail()
        val facebookProfilePicUrl = userPreferences.getUserFacebookProfilePicUrl()
        val googleProfilePicUrl = userPreferences.getUserGoogleProfilePicUrl()
        val accessToken = userPreferences.getAccessToken()
        val refreshToken = userPreferences.getRefreshToken()
        val rolesString = userPreferences.getUserRoles()

        if (userId !== null && accessToken != null && refreshToken != null && rolesString != null) {
            val roles: List<Role> =
                try {
                    Moshi.Builder().build().adapter<List<Role>>(
                        Types.newParameterizedType(
                            List::class.java,
                            Role::class.java
                        )
                    ).fromJson(rolesString) ?: emptyList()
                } catch (e: AssertionError) {
                    Logger.record(e)
                    emptyList()
                }

            return AppUser(
                userId, userName, userEmail, facebookProfilePicUrl,
                googleProfilePicUrl, accessToken, refreshToken, roles
            )
        }
        return null
    }

    fun markUserOnBoardingComplete() {
        userPreferences.setOnBoardingComplete(true)
    }

    fun isOnBoardingComplete() = userPreferences.getOnBoardingComplete()

    fun saveDeviceId(deviceId: String) = userPreferences.setDeviceId(deviceId)

    fun getDeviceId() = userPreferences.getDeviceId()

    fun saveUserRatingMessage(rate: Int, message: String): Flow<String> = flow {
        emit(
            networkService.doMessageCall(
                MessageRequest("USER_ANDROID_RATING", "Rating: $rate | Message: $message")
            )
        )
    }.map { it.message }


    fun sendFirebaseToken(token: String): Flow<String> = flow {
        emit(networkService.doFirebaseTokenCall(FirebaseTokenRequest(token)))
    }.map { it.message }

    fun sendFeedback(message: String): Flow<String> = flow {
        emit(networkService.doMessageCall(MessageRequest("USER_ANDROID_FEEDBACK", message)))
    }.map { it.message }

    fun getFirebaseToken(): String? = userPreferences.getFirebaseToken()

    fun setFirebaseToken(token: String) = userPreferences.setFirebaseToken(token)

    fun getFirebaseTokenSent(): Boolean = userPreferences.getFirebaseTokenSent()

    fun setFirebaseTokenSent() = userPreferences.setFirebaseTokenSent()

    fun doLogoutCall(): Flow<String> = flow {
        emit(networkService.doLogoutCall())
    }.map { it.message }
}