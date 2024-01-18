package com.whereismymotivation.data.remote.apis.mentor

import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.remote.RequestHeaders
import com.whereismymotivation.data.remote.response.ApiDataResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MentorApi {

    @GET(Endpoints.MENTOR_DETAIL)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun mentorDetails(
        @Path("id") mentorId: String
    ): ApiDataResponse<Mentor>

    @GET(Endpoints.RECOMMENDED_MENTORS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun recommendedMentors(
        @Query("pageNumber") pageNumber: Int, @Query("pageItemCount") pageItemCount: Int
    ): ApiDataResponse<List<Mentor>>
}