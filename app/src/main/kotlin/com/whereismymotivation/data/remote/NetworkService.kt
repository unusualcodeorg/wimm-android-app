package com.whereismymotivation.data.remote

import com.whereismymotivation.data.model.Auth
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.model.MetaContent
import com.whereismymotivation.data.model.SubscriptionInfo
import com.whereismymotivation.data.model.Token
import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.data.model.UniversalSearchResult
import com.whereismymotivation.data.remote.request.*
import com.whereismymotivation.data.remote.response.*
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {

    @POST(Endpoints.AUTH_LOGIN_BASIC)
    @Headers(RequestHeaders.Key.AUTH_PUBLIC)
    suspend fun basicLogin(@Body request: BasicLoginRequest): ApiDataResponse<Auth>

    @POST(Endpoints.AUTH_LOGIN_FACEBOOK)
    @Headers(RequestHeaders.Key.AUTH_PUBLIC)
    suspend fun facebookLogin(@Body request: FacebookLoginRequest): ApiDataResponse<Auth>

    @POST(Endpoints.AUTH_LOGIN_GOOGLE)
    @Headers(RequestHeaders.Key.AUTH_PUBLIC)
    suspend fun googleLogin(@Body request: GoogleLoginRequest): ApiDataResponse<Auth>

    @POST(Endpoints.AUTH_REFRESH_TOKEN)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    fun refreshToken(@Body request: RefreshTokenRequest): Call<ApiDataResponse<Token>>

    @DELETE(Endpoints.AUTH_LOGOUT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun logout(): ApiGeneralResponse

    @POST(Endpoints.SUBSCRIPTION_SUBSCRIBE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun subscriptionSubscribe(@Body request: SubscriptionModifyRequest): ApiGeneralResponse

    @POST(Endpoints.SUBSCRIPTION_UNSUBSCRIBE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun subscriptionUnsubscribe(@Body request: SubscriptionModifyRequest): ApiGeneralResponse

    @GET(Endpoints.SUBSCRIPTION_MENTORS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun subscriptionMentors(): ApiDataResponse<List<Mentor>>

    @GET(Endpoints.SUBSCRIPTION_TOPICS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun subscriptionTopics(): ApiDataResponse<List<Topic>>

    @GET(Endpoints.SUBSCRIPTION_INFO_MENTOR)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun subscriptionInfoMentor(@Path("id") mentorId: String): ApiDataResponse<SubscriptionInfo>

    @GET(Endpoints.SUBSCRIPTION_INFO_TOPIC)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun subscriptionInfoTopic(@Path("id") topicId: String): ApiDataResponse<SubscriptionInfo>

    @GET(Endpoints.SIMILAR_CONTENTS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun similarContents(
        @Path("id") contentId: String,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageItemCount") pageItemCount: Int,
    ): ApiDataResponse<List<Content>>

    @GET(Endpoints.CONTENTS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun contents(
        @Query("pageNumber") pageNumber: Int,
        @Query("pageItemCount") pageItemCount: Int,
        @Query("empty") empty: Boolean
    ): ApiDataResponse<List<Content>>

    @GET(Endpoints.MENTOR_CONTENTS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun mentorContents(
        @Path("id") mentorId: String,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageItemCount") pageItemCount: Int
    ): ApiDataResponse<List<Content>>

    @GET(Endpoints.TOPIC_CONTENTS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun topicContents(
        @Path("id") topicId: String,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageItemCount") pageItemCount: Int
    ): ApiDataResponse<List<Content>>

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

    @GET(Endpoints.MENTOR_DETAIL)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun mentorDetails(@Path("id") mentorId: String): ApiDataResponse<Mentor>

    @GET(Endpoints.TOPIC_DETAIL)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun topicDetails(@Path("id") topicId: String): ApiDataResponse<Topic>

    @GET(Endpoints.SEARCH)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun search(@Query("query") query: String): ApiDataResponse<List<UniversalSearchResult>>

    @GET(Endpoints.SEARCH)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun filteredSearch(
        @Query("query") query: String, @Query("filter") filter: String
    ): ApiDataResponse<List<UniversalSearchResult>>

    @POST(Endpoints.MESSAGE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun message(@Body request: MessageRequest): ApiGeneralResponse

    @PUT(Endpoints.PROFILE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun firebaseToken(@Body request: FirebaseTokenRequest): ApiGeneralResponse

    @GET(Endpoints.META_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun metaContent(@Query("url") url: String): ApiDataResponse<MetaContent>

    @GET(Endpoints.MY_BOX_CONTENTS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun myBoxContents(
        @Query("pageNumber") pageNumber: Int, @Query("pageItemCount") pageItemCount: Int
    ): ApiDataResponse<List<Content>>

    @POST(Endpoints.CREATE_PRIVATE_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun createPrivateContent(@Body request: MetaContent): ApiDataResponse<Content>

    @DELETE(Endpoints.DELETE_PRIVATE_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun removePrivateContent(@Path("id") contentId: String): ApiGeneralResponse

    @POST(Endpoints.BOOKMARK_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun contentBookmark(@Body request: ContentBookmarkRequest): ApiGeneralResponse

    @DELETE(Endpoints.REMOVE_CONTENT_BOOKMARK)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun removeContentBookmark(@Path("id") contentId: String): ApiGeneralResponse

    @POST(Endpoints.SUBMIT_PRIVATE_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun submitPrivateContent(@Body request: ContentSubmissionRequest): ApiGeneralResponse

    @POST(Endpoints.UNSUBMIT_PRIVATE_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun unsubmitPrivateContent(@Body request: ContentSubmissionRequest): ApiGeneralResponse

    @POST(Endpoints.PUBLISH_GENERAL_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun publishGeneral(@Body request: ContentSubmissionRequest): ApiGeneralResponse

    @GET(Endpoints.CONTENT_DETAIL)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun contentDetails(@Path("id") contentId: String): ApiDataResponse<Content>

    @POST(Endpoints.CONTENT_MARK_LIKE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun contentMarkLike(@Body request: ContentSubmissionRequest): ApiGeneralResponse

    @POST(Endpoints.CONTENT_MARK_UNLIKE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun contentMarkUnlike(@Body request: ContentSubmissionRequest): ApiGeneralResponse

    @POST(Endpoints.CONTENT_MARK_VIEW)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun contentMarkView(@Body request: ContentSubmissionRequest): ApiGeneralResponse

    @POST(Endpoints.CONTENT_MARK_SHARE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun contentMarkShare(@Body request: ContentSubmissionRequest): ApiGeneralResponse

    @POST(Endpoints.STORAGE_MOOD)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun moodStorage(@Body request: MoodsRequest): ApiGeneralResponse

    @POST(Endpoints.STORAGE_JOURNAL)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun journalStorage(@Body request: JournalsRequest): ApiGeneralResponse
}