package com.whereismymotivation.data.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.whereismymotivation.data.local.prefs.UserPreferences
import com.whereismymotivation.data.model.Auth
import com.whereismymotivation.data.model.Role
import com.whereismymotivation.data.model.User
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

    fun userExists() = userPreferences.getUserId() != null

    fun saveCurrentAuth(auth: Auth) {
        removeCurrentUser()
        val user = auth.user
        userPreferences.setUserId(user.id)
        user.name?.let { userPreferences.setUserName(it) }
        user.email?.let { userPreferences.setUserEmail(it) }

        val roles: String =
            try {
                Moshi.Builder().build().adapter<List<Role>>(
                    Types.newParameterizedType(
                        List::class.java,
                        Role::class.java
                    )
                ).toJson(user.roles)
            } catch (e: AssertionError) {
                Logger.record(e)
                "[]"
            }

        userPreferences.setUserRoles(roles)
        userPreferences.setAccessToken(auth.token.accessToken)
        userPreferences.setRefreshToken(auth.token.refreshToken)
        userPreferences.setUserProfileProfilePicUrl(user.profilePicUrl)
    }

    fun removeCurrentUser() {
        userPreferences.removeUserId()
        userPreferences.removeUserName()
        userPreferences.removeUserEmail()
        userPreferences.removeAccessToken()
        userPreferences.removeRefreshToken()
        userPreferences.removeUserProfilePicUrl()
        userPreferences.removeOnBoardingComplete()
        userPreferences.removeUserRoles()
    }

    fun getCurrentUser(): User? {

        val userId = userPreferences.getUserId()
        val userName = userPreferences.getUserName()
        val userEmail = userPreferences.getUserEmail()
        val profilePicUrl = userPreferences.getUserProfilePicUrlUrl()
        val rolesString = userPreferences.getUserRoles()

        if (userId !== null && rolesString != null) {
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

            return User(
                userId, userName, userEmail, profilePicUrl, roles
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
            networkService.message(
                MessageRequest("USER_ANDROID_RATING", "Rating: $rate | Message: $message")
            )
        )
    }.map { it.message }


    fun sendFirebaseToken(token: String): Flow<String> = flow {
        emit(networkService.firebaseToken(FirebaseTokenRequest(token)))
    }.map { it.message }

    fun sendFeedback(message: String): Flow<String> = flow {
        emit(networkService.message(MessageRequest("USER_ANDROID_FEEDBACK", message)))
    }.map { it.message }

    fun getFirebaseToken(): String? = userPreferences.getFirebaseToken()

    fun setFirebaseToken(token: String) = userPreferences.setFirebaseToken(token)

    fun getFirebaseTokenSent(): Boolean = userPreferences.getFirebaseTokenSent()

    fun setFirebaseTokenSent() = userPreferences.setFirebaseTokenSent()
}