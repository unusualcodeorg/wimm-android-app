package com.whereismymotivation.data.remote.apis.subscription

import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.model.SubscriptionInfo
import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.data.remote.RequestHeaders
import com.whereismymotivation.data.remote.apis.subscription.request.SubscriptionModifyRequest
import com.whereismymotivation.data.remote.response.ApiDataResponse
import com.whereismymotivation.data.remote.response.ApiGeneralResponse
import retrofit2.http.*

interface SubscriptionApi {

    @POST(Endpoints.SUBSCRIPTION_SUBSCRIBE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun subscriptionSubscribe(
        @Body request: SubscriptionModifyRequest
    ): ApiGeneralResponse

    @POST(Endpoints.SUBSCRIPTION_UNSUBSCRIBE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun subscriptionUnsubscribe(
        @Body request: SubscriptionModifyRequest
    ): ApiGeneralResponse

    @GET(Endpoints.SUBSCRIPTION_MENTORS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun subscriptionMentors(): ApiDataResponse<List<Mentor>>

    @GET(Endpoints.SUBSCRIPTION_TOPICS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun subscriptionTopics(): ApiDataResponse<List<Topic>>

    @GET(Endpoints.SUBSCRIPTION_INFO_MENTOR)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun subscriptionInfoMentor(
        @Path("id") mentorId: String
    ): ApiDataResponse<SubscriptionInfo>

    @GET(Endpoints.SUBSCRIPTION_INFO_TOPIC)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun subscriptionInfoTopic(
        @Path("id") topicId: String
    ): ApiDataResponse<SubscriptionInfo>

    @GET(Endpoints.RECOMMENDED_MENTORS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun recommendedMentors(
        @Query("pageNumber") pageNumber: Int, @Query("pageItemCount") pageItemCount: Int
    ): ApiDataResponse<List<Mentor>>

    @GET(Endpoints.RECOMMENDED_TOPICS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun recommendedTopics(
        @Query("pageNumber") pageNumber: Int, @Query("pageItemCount") pageItemCount: Int
    ): ApiDataResponse<List<Topic>>

}