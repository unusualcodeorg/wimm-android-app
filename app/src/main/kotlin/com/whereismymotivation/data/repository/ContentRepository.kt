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
        emit(networkService.doSimilarContentListCall(contentId))
    }.map { it.data }

    fun fetchHomeFeedList(
        pageNumber: Int,
        pageItemCount: Int,
        empty: Boolean
    ): Flow<List<Content>> = flow {
        emit(networkService.doContentListCall(pageNumber, pageItemCount, empty))
    }.map { it.data }

    fun fetchMyBoxContentList(pageNumber: Int, pageItemCount: Int): Flow<List<Content>> = flow {
        emit(networkService.doMyBoxContentListCall(pageNumber, pageItemCount))
    }.map { it.data }

    fun fetchMetaContent(url: String): Flow<MetaContent> = flow {
        emit(networkService.doMetaContentCall(url))
    }.map { it.data }

    fun createPrivateContent(content: MetaContent): Flow<Content> = flow {
        emit(networkService.doCreatePrivateContentCall(content))
    }.map { it.data }

    fun removePrivateContent(contentId: String): Flow<String> = flow {
        emit(networkService.doRemovePrivateContentCall(contentId))
    }.map { it.message }

    fun bookmarkContent(contentId: String): Flow<String> = flow {
        emit(networkService.doContentBookmarkCall(ContentBookmarkRequest(contentId)))
    }.map { it.message }

    fun removeContentBookmark(contentId: String): Flow<String> = flow {
        emit(networkService.doRemoveContentBookmarkCall(contentId))
    }.map { it.message }

    fun submitPrivateContent(contentId: String): Flow<String> = flow {
        emit(
            networkService.doSubmitPrivateContentCall(ContentSubmissionRequest(contentId))
        )
    }.map { it.message }

    fun unsubmitPrivateContent(contentId: String): Flow<String> = flow {
        emit(
            networkService.doUnsubmitPrivateContentCall(ContentSubmissionRequest(contentId))
        )
    }.map { it.message }

    fun publishGeneralContent(contentId: String): Flow<String> = flow {
        emit(
            networkService.doPublishGeneralContent(ContentSubmissionRequest(contentId))
        )
    }.map { it.message }

    fun getFeedNextPageNumber(): Int = contentPreferences.getFeedNextPageNumber()

    fun setFeedNextPageNumber(pageNumber: Int) =
        contentPreferences.setFeedNextPageNumber(pageNumber)

    fun getFeedLastSeen(): Long = contentPreferences.getFeedLastSeen()

    fun markFeedLastSeen() = contentPreferences.setFeedLastSeen(System.currentTimeMillis())

    fun getContentDetails(contentId: String): Flow<Content> = flow {
        emit(networkService.doContentDetailsCall(contentId))
    }.map { it.data }

    fun markContentLike(contentId: String): Flow<String> = flow {
        emit(networkService.doContentMarkLikeCall(ContentSubmissionRequest(contentId)))
    }.map { it.message }

    fun markContentUnlike(contentId: String): Flow<String> = flow {
        emit(
            networkService.doContentMarkUnlikeCall(ContentSubmissionRequest(contentId))
        )
    }.map { it.message }

    fun markContentView(contentId: String): Flow<String> = flow {
        emit(networkService.doContentMarkViewCall(ContentSubmissionRequest(contentId)))
    }.map { it.message }

    fun markContentShare(contentId: String): Flow<String> = flow {
        emit(
            networkService.doContentMarkShareCall(ContentSubmissionRequest(contentId))
        )
    }.map { it.message }

}
