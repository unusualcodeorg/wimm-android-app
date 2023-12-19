package com.whereismymotivation.ui.feed

import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.repository.ContentRepository
import com.whereismymotivation.data.repository.RemoteConfigRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.log.Logger
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    loader: Loader,
    messenger: Messenger,
    remoteConfigRepository: RemoteConfigRepository,
    private val navigator: Navigator,
    private val contentRepository: ContentRepository,
) : BaseViewModel(networkHelper, loader, messenger) {

    companion object {
        const val TAG = "FeedViewModel"
    }

    private val _contents = MutableStateFlow<List<Content>>(emptyList())

    val contents = _contents.asStateFlow()

    private var loading = false
    private var notMoreToLoad = false

    //    private var pageItemCount = remoteConfigRepository.getHomePageContentCount()
//    private var startPageNumber = contentRepository.getFeedNextPageNumber()
    private var startPageNumber = 1
    private var pageItemCount = 10
    private var currentPageNumber = startPageNumber
    private var reverse: Boolean = false

    init {
        if ((System.currentTimeMillis() - contentRepository.getFeedLastSeen()) / 1000 / 60 / 60 > 3) {
            contentRepository.setFeedNextPageNumber(1)
            startPageNumber = 1
            currentPageNumber = startPageNumber
        }

        loadContents(currentPageNumber, pageItemCount)
    }

    fun loadMoreContents() {
        Logger.d(TAG, "loadMoreContents")
        loadContents(currentPageNumber, pageItemCount)
    }

    fun selectContent(content: Content) {
    }

    private fun loadContents(pageNumber: Int, pageItemCount: Int) {
        if (loading || notMoreToLoad) return
        loading = true

        launchNetwork(error = { loading = false }) {
            contentRepository.fetchHomeFeedList(pageNumber, pageItemCount, contents.value.isEmpty())
                .collect {
                    if (it.isEmpty()) {
                        notMoreToLoad = true
                    } else {
                        currentPageNumber++
                        contentRepository.setFeedNextPageNumber(currentPageNumber)
                        _contents.value = _contents.value + it
                    }
                    loading = false
                }
        }
    }
}