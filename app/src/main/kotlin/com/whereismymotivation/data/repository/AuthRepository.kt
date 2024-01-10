package com.whereismymotivation.data.repository

import com.whereismymotivation.data.model.Auth
import com.whereismymotivation.data.remote.apis.auth.AuthApi
import com.whereismymotivation.data.remote.apis.auth.BasicAuthRequest
import com.whereismymotivation.data.remote.apis.auth.FacebookAuthRequest
import com.whereismymotivation.data.remote.apis.auth.GoogleAuthRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
) {
    fun basicLogin(email: String, password: String): Flow<Auth> = flow {
        emit(authApi.basicLogin(BasicAuthRequest(email, password)))
    }.map { it.data }

    fun facebookLogin(userId: String, token: String): Flow<Auth> = flow {
        emit(authApi.facebookLogin(FacebookAuthRequest(userId, token)))
    }.map { it.data }

    fun googleLogin(userId: String, token: String): Flow<Auth> = flow {
        emit(authApi.googleLogin(GoogleAuthRequest(userId, token)))
    }.map { it.data }

    fun logout(): Flow<String> = flow {
        emit(authApi.logout())
    }.map { it.message }

}
