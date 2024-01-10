package com.whereismymotivation.data.repository

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
    fun doFacebookLogin(userId: String, token: String): Flow<Auth> = flow {
        emit(networkService.facebookLogin(FacebookLoginRequest(userId, token)))
    }.map { it.data }

    fun doGoogleLogin(userId: String, token: String): Flow<Auth> = flow {
        emit(networkService.googleLogin(GoogleLoginRequest(userId, token)))
    }.map { it.data }

    fun basicLogin(email: String, password: String): Flow<Auth> = flow {
        emit(networkService.basicLogin(BasicLoginRequest(email, password)))
    }.map { it.data }

}
