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

    @POST(Endpoints.LOGIN_DEVICE)
    @Headers(RequestHeaders.Key.AUTH_PUBLIC)
    suspend fun doBasicLoginCall(@Body request: BasicLoginRequest): ApiDataResponse<Auth>

    @POST(Endpoints.LOGIN_FACEBOOK)
    @Headers(RequestHeaders.Key.AUTH_PUBLIC)
    suspend fun doFacebookLoginCall(@Body request: FacebookLoginRequest): ApiDataResponse<Auth>

    @POST(Endpoints.LOGIN_GOOGLE)
    @Headers(RequestHeaders.Key.AUTH_PUBLIC)
    suspend fun doGoogleLoginCall(@Body request: GoogleLoginRequest): ApiDataResponse<Auth>

    @POST(Endpoints.REFRESH_TOKEN)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    fun doRefreshTokenCall(@Body request: RefreshTokenRequest): Call<ApiDataResponse<Token>>

    @DELETE(Endpoints.LOGOUT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doLogoutCall(): ApiGeneralResponse

    @POST(Endpoints.SUBSCRIPTION_SUBSCRIBE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doSubscriptionSubscribeCall(@Body request: SubscriptionModifyRequest): ApiGeneralResponse

    @POST(Endpoints.SUBSCRIPTION_UNSUBSCRIBE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doSubscriptionUnsubscribeCall(@Body request: SubscriptionModifyRequest): ApiGeneralResponse

    @GET(Endpoints.SUBSCRIPTION_MENTOR_LIST)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doSubscriptionMentorListCall(): ApiDataResponse<List<Mentor>>

    @GET(Endpoints.SUBSCRIPTION_TOPIC_LIST)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doSubscriptionTopicListCall(): ApiDataResponse<List<Topic>>

    @GET(Endpoints.SUBSCRIPTION_INFO_MENTOR)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doSubscriptionInfoMentorCall(@Path("id") mentorId: String): ApiDataResponse<SubscriptionInfo>

    @GET(Endpoints.SUBSCRIPTION_INFO_TOPIC)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doSubscriptionInfoTopicCall(@Path("id") topicId: String): ApiDataResponse<SubscriptionInfo>

    @GET(Endpoints.SIMILAR_CONTENT_LIST)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doSimilarContentListCall(@Query("contentId") contentId: String): ApiDataResponse<List<Content>>

    @GET(Endpoints.CONTENT_LIST)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doContentListCall(
        @Query("pageNumber") pageNumber: Int,
        @Query("pageItemCount") pageItemCount: Int,
        @Query("empty") empty: Boolean
    ): ApiDataResponse<List<Content>>

    @GET(Endpoints.MENTOR_CONTENT_LIST)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doMentorContentListCall(
        @Path("id") mentorId: String,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageItemCount") pageItemCount: Int
    ): ApiDataResponse<List<Content>>

    @GET(Endpoints.TOPIC_CONTENT_LIST)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doTopicContentListCall(
        @Path("id") topicId: String,
        @Query("pageNumber") pageNumber: Int,
        @Query("pageItemCount") pageItemCount: Int
    ): ApiDataResponse<List<Content>>

    @GET(Endpoints.RECOMMENDED_MENTOR_LIST)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doRecommendedMentorListCall(
        @Query("pageNumber") pageNumber: Int, @Query("pageItemCount") pageItemCount: Int
    ): ApiDataResponse<List<Mentor>>

    @GET(Endpoints.RECOMMENDED_TOPIC_LIST)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doRecommendedTopicListCall(
        @Query("pageNumber") pageNumber: Int, @Query("pageItemCount") pageItemCount: Int
    ): ApiDataResponse<List<Topic>>

    @GET(Endpoints.MENTOR_DETAIL)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doMentorDetailsCall(@Path("id") mentorId: String): ApiDataResponse<Mentor>

    @GET(Endpoints.TOPIC_DETAIL)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doTopicDetailsCall(@Path("id") topicId: String): ApiDataResponse<Topic>

    @GET(Endpoints.SEARCH)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doSearchCall(@Query("query") query: String): ApiDataResponse<List<UniversalSearchResult>>

    @GET(Endpoints.SEARCH)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doFilteredSearchCall(
        @Query("query") query: String, @Query("filter") filter: String
    ): ApiDataResponse<List<UniversalSearchResult>>

    @POST(Endpoints.MESSAGE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doMessageCall(@Body request: MessageRequest): ApiGeneralResponse

    @PUT(Endpoints.PROFILE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doFirebaseTokenCall(@Body request: FirebaseTokenRequest): ApiGeneralResponse

    @GET(Endpoints.META_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doMetaContentCall(@Query("url") url: String): ApiDataResponse<MetaContent>

    @GET(Endpoints.MY_BOX_CONTENT_LIST)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doMyBoxContentListCall(
        @Query("pageNumber") pageNumber: Int, @Query("pageItemCount") pageItemCount: Int
    ): ApiDataResponse<List<Content>>

    @POST(Endpoints.CREATE_PRIVATE_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doCreatePrivateContentCall(@Body request: MetaContent): ApiDataResponse<Content>

    @DELETE(Endpoints.DELETE_PRIVATE_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doRemovePrivateContentCall(@Path("id") contentId: String): ApiGeneralResponse

    @POST(Endpoints.BOOKMARK_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doContentBookmarkCall(@Body request: ContentBookmarkRequest): ApiGeneralResponse

    @DELETE(Endpoints.REMOVE_CONTENT_BOOKMARK)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doRemoveContentBookmarkCall(@Path("contentId") contentId: String): ApiGeneralResponse

    @POST(Endpoints.SUBMIT_PRIVATE_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doSubmitPrivateContentCall(@Body request: ContentSubmissionRequest): ApiGeneralResponse

    @POST(Endpoints.UNSUBMIT_PRIVATE_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doUnsubmitPrivateContentCall(@Body request: ContentSubmissionRequest): ApiGeneralResponse

    @POST(Endpoints.PUBLISH_GENERAL_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doPublishGeneralContent(@Body request: ContentSubmissionRequest): ApiGeneralResponse

    @GET(Endpoints.CONTENT_DETAIL)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doContentDetailsCall(@Path("id") contentId: String): ApiDataResponse<Content>

    @POST(Endpoints.CONTENT_MARK_LIKE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doContentMarkLikeCall(@Body request: ContentSubmissionRequest): ApiGeneralResponse

    @POST(Endpoints.CONTENT_MARK_UNLIKE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doContentMarkUnlikeCall(@Body request: ContentSubmissionRequest): ApiGeneralResponse

    @POST(Endpoints.CONTENT_MARK_VIEW)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doContentMarkViewCall(@Body request: ContentSubmissionRequest): ApiGeneralResponse

    @POST(Endpoints.CONTENT_MARK_SHARE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doContentMarkShareCall(@Body request: ContentSubmissionRequest): ApiGeneralResponse

    @POST(Endpoints.STORAGE_MOOD)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doMoodStorageCall(@Body request: MoodsRequest): ApiGeneralResponse

    @POST(Endpoints.STORAGE_JOURNAL)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun doJournalStorageCall(@Body request: JournalsRequest): ApiGeneralResponse
}