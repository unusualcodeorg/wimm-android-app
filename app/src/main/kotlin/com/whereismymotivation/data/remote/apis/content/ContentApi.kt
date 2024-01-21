package com.whereismymotivation.data.remote.apis.content

import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.MetaContent
import com.whereismymotivation.data.model.UniversalSearchResult
import com.whereismymotivation.data.remote.RequestHeaders
import com.whereismymotivation.data.remote.apis.content.request.ContentBookmarkRequest
import com.whereismymotivation.data.remote.apis.content.request.ContentSubmissionRequest
import com.whereismymotivation.data.remote.response.*
import retrofit2.http.*

interface ContentApi {

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

    @GET(Endpoints.META_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun metaContent(
        @Query("url") url: String
    ): ApiDataResponse<MetaContent>

    @GET(Endpoints.MY_BOX_CONTENTS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun myBoxContents(
        @Query("pageNumber") pageNumber: Int, @Query("pageItemCount") pageItemCount: Int
    ): ApiDataResponse<List<Content>>

    @POST(Endpoints.CREATE_PRIVATE_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun createPrivateContent(
        @Body request: MetaContent
    ): ApiDataResponse<Content>

    @DELETE(Endpoints.DELETE_PRIVATE_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun removePrivateContent(
        @Path("id") contentId: String
    ): ApiGeneralResponse

    @POST(Endpoints.BOOKMARK_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun contentBookmark(
        @Body request: ContentBookmarkRequest
    ): ApiGeneralResponse

    @DELETE(Endpoints.REMOVE_CONTENT_BOOKMARK)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun removeContentBookmark(
        @Path("id") contentId: String
    ): ApiGeneralResponse

    @PUT(Endpoints.SUBMIT_PRIVATE_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun submitPrivateContent(
        @Body request: ContentSubmissionRequest
    ): ApiGeneralResponse

    @PUT(Endpoints.UNSUBMIT_PRIVATE_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun unsubmitPrivateContent(
        @Body request: ContentSubmissionRequest
    ): ApiGeneralResponse

    @PUT(Endpoints.PUBLISH_GENERAL_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun publishGeneral(
        @Body request: ContentSubmissionRequest
    ): ApiGeneralResponse

    @PUT(Endpoints.UNPUBLISH_GENERAL_CONTENT)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun unpublishGeneral(
        @Body request: ContentSubmissionRequest
    ): ApiGeneralResponse

    @GET(Endpoints.CONTENT_DETAIL)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun contentDetails(
        @Path("id") contentId: String
    ): ApiDataResponse<Content>

    @POST(Endpoints.CONTENT_MARK_LIKE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun contentMarkLike(
        @Body request: ContentSubmissionRequest
    ): ApiGeneralResponse

    @POST(Endpoints.CONTENT_MARK_UNLIKE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun contentMarkUnlike(
        @Body request: ContentSubmissionRequest
    ): ApiGeneralResponse

    @POST(Endpoints.CONTENT_MARK_VIEW)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun contentMarkView(
        @Body request: ContentSubmissionRequest
    ): ApiGeneralResponse

    @POST(Endpoints.CONTENT_MARK_SHARE)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun contentMarkShare(
        @Body request: ContentSubmissionRequest
    ): ApiGeneralResponse

    @GET(Endpoints.SEARCH)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun search(
        @Query("query") query: String
    ): ApiDataResponse<List<UniversalSearchResult>>

    @GET(Endpoints.SEARCH)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    suspend fun filteredSearch(
        @Query("query") query: String, @Query("filter") filter: String
    ): ApiDataResponse<List<UniversalSearchResult>>

}