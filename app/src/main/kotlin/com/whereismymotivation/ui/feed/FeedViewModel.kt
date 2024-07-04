package com.whereismymotivation.ui.feed

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.repository.ContentRepository
import com.whereismymotivation.data.repository.RemoteConfigRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.browser.ContentBrowser
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.share.Payload
import com.whereismymotivation.ui.common.share.Sharer
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    loader: Loader,
    messenger: Messenger,
    remoteConfigRepository: RemoteConfigRepository,
    private val navigator: Navigator,
    private val sharer: Sharer<Content>,
    private val contentRepository: ContentRepository,
    private val contentBrowser: ContentBrowser
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "FeedViewModel"
    }

    private val _contents = mutableStateListOf<Content>()

    val contents: List<Content> = _contents

    private var loading = false
    private var pageItemCount = remoteConfigRepository.getHomePageContentCount()
    private var startPageNumber = runBlocking { contentRepository.getFeedNextPageNumber() }
    private var currentPageNumber = startPageNumber

    init {
        val lastSeenTime = runBlocking { contentRepository.getFeedLastSeen() }
        if ((System.currentTimeMillis() - lastSeenTime) / 1000 / 60 / 60 > 3) {
            rotateFeedList()
        }

        viewModelScope.launch {
            delay(50) // Fix for viewModel recomposition by Navigation
            loadMoreContents()
        }
    }

    private fun rotateFeedList(reload: Boolean = false) {
        viewModelScope.launch { contentRepository.setFeedNextPageNumber(1) }
        startPageNumber = 1
        currentPageNumber = startPageNumber
        if (reload) loadMoreContents()
    }

    fun loadMoreContents() {
        loadContents(currentPageNumber, pageItemCount)
    }

    fun selectContent(content: Content) {
        contentBrowser.show(content)
    }

    private fun loadContents(pageNumber: Int, pageItemCount: Int) {
        if (loading) return
        loading = true

        launchNetwork(error = { loading = false }) {
            contentRepository.fetchHomeFeedContents(pageNumber, pageItemCount, _contents.isEmpty())
                .collect {
                    if (it.isEmpty()) {
                        rotateFeedList(true)
                    } else {
                        currentPageNumber++
                        contentRepository.setFeedNextPageNumber(currentPageNumber)
                        _contents.addAll(it)
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
                val index = _contents.indexOf(content)
                if (index > -1) {
                    _contents[index] = content.copy(
                        liked = liked,
                        likes = if (liked) content.likes?.plus(1) else content.likes?.minus(1)
                    )
                }
            }
        }
    }

    fun shareContent(content: Content) {
        sharer.share(Payload.text(content))
    }

    fun shareWhatsappContent(content: Content) {
        sharer.share(Payload.whatsappText(content))
    }

    override fun onCleared() {
        viewModelScope.launch { contentRepository.markFeedLastSeen() }
        super.onCleared()
    }
}