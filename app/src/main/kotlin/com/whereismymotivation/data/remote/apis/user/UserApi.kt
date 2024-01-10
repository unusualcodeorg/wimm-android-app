package com.whereismymotivation.data.remote.apis.user

import com.whereismymotivation.data.remote.RequestHeaders
import com.whereismymotivation.data.remote.apis.user.request.FirebaseTokenRequest
import com.whereismymotivation.data.remote.apis.user.request.JournalsRequest
import com.whereismymotivation.data.remote.apis.user.request.MessageRequest
import com.whereismymotivation.data.remote.apis.user.request.MoodsRequest
import com.whereismymotivation.data.remote.response.*
import retrofit2.http.*

interface UserApi {

    @POST(Endpoints.MESSAGE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun message(@Body request: MessageRequest): ApiGeneralResponse

    @PUT(Endpoints.PROFILE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun firebaseToken(@Body request: FirebaseTokenRequest): ApiGeneralResponse

    @POST(Endpoints.STORAGE_MOOD)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun moodStorage(@Body request: MoodsRequest): ApiGeneralResponse

    @POST(Endpoints.STORAGE_JOURNAL)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun journalStorage(@Body request: JournalsRequest): ApiGeneralResponse
}