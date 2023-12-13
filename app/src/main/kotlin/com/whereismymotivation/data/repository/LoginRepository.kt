package com.whereismymotivation.data.repository

import com.whereismymotivation.data.model.AppUser
import com.whereismymotivation.data.model.Auth
import com.whereismymotivation.data.remote.NetworkService
import com.whereismymotivation.data.remote.request.BasicLoginRequest
import com.whereismymotivation.data.remote.request.FacebookLoginRequest
import com.whereismymotivation.data.remote.request.GoogleLoginRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val networkService: NetworkService,
) {
    fun doFacebookLogin(userId: String, token: String): Flow<AppUser> = flow {
        emit(networkService.doFacebookLoginCall(FacebookLoginRequest(userId, token)))
    }.map { saveUserData(it.data) }

    fun doGoogleLogin(userId: String, token: String): Flow<AppUser> = flow {
        emit(networkService.doGoogleLoginCall(GoogleLoginRequest(userId, token)))
    }.map { saveUserData(it.data) }

    fun basicLogin(email: String, password: String): Flow<AppUser> = flow {
        emit(networkService.doBasicLoginCall(BasicLoginRequest(email, password)))
    }.map { saveUserData(it.data) }

    private fun saveUserData(data: Auth): AppUser = AppUser(
        data.user.id,
        data.user.name,
        data.user.email,
        data.user.facebookProfilePicUrl,
        data.user.googleProfilePicUrl,
        data.token.accessToken,
        data.token.refreshToken,
        data.roles
    )
}
