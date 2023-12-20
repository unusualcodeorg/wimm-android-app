package com.whereismymotivation.ui.feed

import androidx.lifecycle.viewModelScope
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.repository.ContentRepository
import com.whereismymotivation.data.repository.RemoteConfigRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    private var pageItemCount = remoteConfigRepository.getHomePageContentCount()
    private var startPageNumber = contentRepository.getFeedNextPageNumber()
    private var currentPageNumber = startPageNumber

    init {
        if ((System.currentTimeMillis() - contentRepository.getFeedLastSeen()) / 1000 / 60 / 60 > 3) {
            rotateFeedList()
        }

        viewModelScope.launch {
            delay(50) // Fix for viewModel recomposition by Navigation
            loadMoreContents()
        }
    }

    private fun rotateFeedList() {
        contentRepository.setFeedNextPageNumber(1)
        startPageNumber = 1
        currentPageNumber = startPageNumber
    }

    fun loadMoreContents() {
        loadContents(currentPageNumber, pageItemCount)
    }

    fun selectContent(content: Content) {
    }

    private fun loadContents(pageNumber: Int, pageItemCount: Int) {
        if (loading) return
        loading = true

        launchNetwork(error = { loading = false }) {
            contentRepository.fetchHomeFeedList(pageNumber, pageItemCount, contents.value.isEmpty())
                .collect {
                    if (it.isEmpty()) {
                        rotateFeedList()
                    } else {
                        currentPageNumber++
                        contentRepository.setFeedNextPageNumber(currentPageNumber)
                        _contents.value = _contents.value + it
                    }
                    loading = false
                }
        }
    }

    fun toggleContentLike(content: Content) {
        launchNetwork {
            val liked = !(content.liked ?: false)
            val call =
                if (liked) contentRepository.markContentLike(content.id) else contentRepository.markContentUnlike(
                    content.id
                )
            call.collect {
                content.liked = liked
            }
        }
    }

    override fun onCleared() {
        contentRepository.markFeedLastSeen()
        super.onCleared()
    }
}