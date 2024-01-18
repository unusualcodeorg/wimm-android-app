package com.whereismymotivation.data.remote.apis.topic

import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.data.remote.RequestHeaders
import com.whereismymotivation.data.remote.response.ApiDataResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface TopicApi {

    @GET(Endpoints.TOPIC_DETAIL)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun topicDetails(
        @Path("id") topicId: String
    ): ApiDataResponse<Topic>

    @GET(Endpoints.RECOMMENDED_TOPICS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun recommendedTopics(
        @Query("pageNumber") pageNumber: Int, @Query("pageItemCount") pageItemCount: Int
    ): ApiDataResponse<List<Topic>>
}