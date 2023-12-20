package com.whereismymotivation.ui.feed

import androidx.compose.runtime.mutableStateListOf
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

    val contents = mutableStateListOf<Content>()

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
            contentRepository.fetchHomeFeedList(pageNumber, pageItemCount, contents.isEmpty())
                .collect {
                    if (it.isEmpty()) {
                        rotateFeedList()
                    } else {
                        currentPageNumber++
                        contentRepository.setFeedNextPageNumber(currentPageNumber)
                        contents.addAll(it)
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
                val index = contents.indexOf(content)
                if (index > -1) {
                    contents[index] = content.copy(
                        liked = liked,
                        likes = if (liked) content.likes?.plus(1) else content.likes?.minus(1)
                    )
                }
            }
        }
    }

    override fun onCleared() {
        contentRepository.markFeedLastSeen()
        super.onCleared()
    }
}