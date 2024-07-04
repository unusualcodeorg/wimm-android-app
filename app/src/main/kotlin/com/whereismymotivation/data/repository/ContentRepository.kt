package com.whereismymotivation.data.repository

import com.whereismymotivation.data.local.prefs.ContentPreferences
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.MetaContent
import com.whereismymotivation.data.remote.apis.content.ContentApi
import com.whereismymotivation.data.remote.apis.content.request.ContentBookmarkRequest
import com.whereismymotivation.data.remote.apis.content.request.ContentSubmissionRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContentRepository @Inject constructor(
    private val contentApi: ContentApi,
    private val contentPreferences: ContentPreferences
) {

    suspend fun fetchSimilarContents(
        contentId: String,
        pageNumber: Int,
        pageItemCount: Int,
    ): Flow<List<Content>> =
        flow {
            emit(contentApi.similarContents(contentId, pageNumber, pageItemCount))
        }.map { it.data }

    suspend fun fetchHomeFeedContents(
        pageNumber: Int,
        pageItemCount: Int,
        empty: Boolean
    ): Flow<List<Content>> =
        flow {
            emit(contentApi.contents(pageNumber, pageItemCount, empty))
        }.map { it.data }

    suspend fun fetchMyBoxContents(pageNumber: Int, pageItemCount: Int): Flow<List<Content>> =
        flow {
            emit(contentApi.myBoxContents(pageNumber, pageItemCount))
        }.map { it.data }

    suspend fun fetchContentDetails(contendId: String): Flow<Content> =
        flow {
            emit(contentApi.contentDetails(contendId))
        }.map { it.data }

    suspend fun fetchMetaContent(url: String): Flow<MetaContent> =
        flow {
            emit(contentApi.metaContent(url))
        }.map { it.data }

    suspend fun createPrivateContent(content: MetaContent): Flow<Content> =
        flow {
            emit(contentApi.createPrivateContent(content))
        }.map { it.data }

    suspend fun removePrivateContent(contentId: String): Flow<String> =
        flow {
            emit(contentApi.removePrivateContent(contentId))
        }.map { it.message }

    suspend fun bookmarkContent(contentId: String): Flow<String> =
        flow {
            emit(contentApi.contentBookmark(ContentBookmarkRequest(contentId)))
        }.map { it.message }

    suspend fun removeContentBookmark(contentId: String): Flow<String> =
        flow {
            emit(contentApi.removeContentBookmark(contentId))
        }.map { it.message }

    suspend fun submitPrivateContent(contentId: String): Flow<String> =
        flow {
            emit(contentApi.submitPrivateContent(ContentSubmissionRequest(contentId)))
        }.map { it.message }

    suspend fun unsubmitPrivateContent(contentId: String): Flow<String> =
        flow {
            emit(contentApi.unsubmitPrivateContent(ContentSubmissionRequest(contentId)))
        }.map { it.message }

    suspend fun publishGeneralContent(contentId: String): Flow<String> =
        flow {
            emit(contentApi.publishGeneral(ContentSubmissionRequest(contentId)))
        }.map { it.message }

    suspend fun unpublishGeneralContent(contentId: String): Flow<String> =
        flow {
            emit(contentApi.unpublishGeneral(ContentSubmissionRequest(contentId)))
        }.map { it.message }

    suspend fun getFeedNextPageNumber(): Int = contentPreferences.getFeedNextPageNumber()

    suspend fun setFeedNextPageNumber(pageNumber: Int) =
        contentPreferences.setFeedNextPageNumber(pageNumber)

    suspend fun getFeedLastSeen(): Long = contentPreferences.getFeedLastSeen()

    suspend fun markFeedLastSeen() = contentPreferences.setFeedLastSeen(System.currentTimeMillis())

    suspend fun getContentDetails(contentId: String): Flow<Content> =
        flow {
            emit(contentApi.contentDetails(contentId))
        }.map { it.data }

    suspend fun markContentLike(contentId: String): Flow<String> =
        flow {
            emit(contentApi.contentMarkLike(ContentSubmissionRequest(contentId)))
        }.map { it.message }

    suspend fun markContentUnlike(contentId: String): Flow<String> =
        flow {
            emit(contentApi.contentMarkUnlike(ContentSubmissionRequest(contentId)))
        }.map { it.message }

    suspend fun markContentView(contentId: String): Flow<String> =
        flow {
            emit(contentApi.contentMarkView(ContentSubmissionRequest(contentId)))
        }.map { it.message }

    suspend fun markContentShare(contentId: String): Flow<String> =
        flow {
            emit(contentApi.contentMarkShare(ContentSubmissionRequest(contentId)))
        }.map { it.message }

}
