package com.whereismymotivation.data.remote.apis.auth

import com.whereismymotivation.data.model.Auth
import com.whereismymotivation.data.remote.RequestHeaders
import com.whereismymotivation.data.remote.response.ApiDataResponse
import com.whereismymotivation.data.remote.response.ApiGeneralResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @POST(Endpoints.AUTH_LOGIN_BASIC)
    @Headers(RequestHeaders.Key.AUTH_PUBLIC)
    suspend fun basicLogin(@Body request: BasicAuthRequest): ApiDataResponse<Auth>

    @POST(Endpoints.AUTH_LOGIN_FACEBOOK)
    @Headers(RequestHeaders.Key.AUTH_PUBLIC)
    suspend fun facebookLogin(@Body request: FacebookAuthRequest): ApiDataResponse<Auth>

    @POST(Endpoints.AUTH_LOGIN_GOOGLE)
    @Headers(RequestHeaders.Key.AUTH_PUBLIC)
    suspend fun googleLogin(@Body request: GoogleAuthRequest): ApiDataResponse<Auth>

    @DELETE(Endpoints.AUTH_LOGOUT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun logout(): ApiGeneralResponse
}