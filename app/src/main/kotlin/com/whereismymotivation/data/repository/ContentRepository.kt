package com.whereismymotivation.data.repository

import com.whereismymotivation.data.local.db.DatabaseService
import com.whereismymotivation.data.local.prefs.ContentPreferences
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.MetaContent
import com.whereismymotivation.data.remote.NetworkService
import com.whereismymotivation.data.remote.request.ContentBookmarkRequest
import com.whereismymotivation.data.remote.request.ContentSubmissionRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContentRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val contentPreferences: ContentPreferences
) {

    fun fetchSimilarContentList(contentId: String): Flow<List<Content>> = flow {
        emit(networkService.similarContentList(contentId))
    }.map { it.data }

    fun fetchHomeFeedList(
        pageNumber: Int,
        pageItemCount: Int,
        empty: Boolean
    ): Flow<List<Content>> = flow {
        emit(networkService.contentList(pageNumber, pageItemCount, empty))
    }.map { it.data }

    fun fetchMyBoxContentList(pageNumber: Int, pageItemCount: Int): Flow<List<Content>> = flow {
        emit(networkService.myBoxContentList(pageNumber, pageItemCount))
    }.map { it.data }

    fun fetchMetaContent(url: String): Flow<MetaContent> = flow {
        emit(networkService.metaContent(url))
    }.map { it.data }

    fun createPrivateContent(content: MetaContent): Flow<Content> = flow {
        emit(networkService.createPrivateContent(content))
    }.map { it.data }

    fun removePrivateContent(contentId: String): Flow<String> = flow {
        emit(networkService.removePrivateContent(contentId))
    }.map { it.message }

    fun bookmarkContent(contentId: String): Flow<String> = flow {
        emit(networkService.contentBookmark(ContentBookmarkRequest(contentId)))
    }.map { it.message }

    fun removeContentBookmark(contentId: String): Flow<String> = flow {
        emit(networkService.removeContentBookmark(contentId))
    }.map { it.message }

    fun submitPrivateContent(contentId: String): Flow<String> = flow {
        emit(
            networkService.submitPrivateContent(ContentSubmissionRequest(contentId))
        )
    }.map { it.message }

    fun unsubmitPrivateContent(contentId: String): Flow<String> = flow {
        emit(
            networkService.unsubmitPrivateContent(ContentSubmissionRequest(contentId))
        )
    }.map { it.message }

    fun publishGeneralContent(contentId: String): Flow<String> = flow {
        emit(
            networkService.publishGeneral(ContentSubmissionRequest(contentId))
        )
    }.map { it.message }

    fun getFeedNextPageNumber(): Int = contentPreferences.getFeedNextPageNumber()

    fun setFeedNextPageNumber(pageNumber: Int) =
        contentPreferences.setFeedNextPageNumber(pageNumber)

    fun getFeedLastSeen(): Long = contentPreferences.getFeedLastSeen()

    fun markFeedLastSeen() = contentPreferences.setFeedLastSeen(System.currentTimeMillis())

    fun getContentDetails(contentId: String): Flow<Content> = flow {
        emit(networkService.contentDetails(contentId))
    }.map { it.data }

    fun markContentLike(contentId: String): Flow<String> = flow {
        emit(networkService.contentMarkLike(ContentSubmissionRequest(contentId)))
    }.map { it.message }

    fun markContentUnlike(contentId: String): Flow<String> = flow {
        emit(
            networkService.contentMarkUnlike(ContentSubmissionRequest(contentId))
        )
    }.map { it.message }

    fun markContentView(contentId: String): Flow<String> = flow {
        emit(networkService.contentMarkView(ContentSubmissionRequest(contentId)))
    }.map { it.message }

    fun markContentShare(contentId: String): Flow<String> = flow {
        emit(
            networkService.contentMarkShare(ContentSubmissionRequest(contentId))
        )
    }.map { it.message }

}
