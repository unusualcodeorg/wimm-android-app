package com.whereismymotivation.ui.content

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.repository.ContentRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.share.Payload
import com.whereismymotivation.ui.common.share.Sharer
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class YoutubeViewModel @Inject constructor(
    loader: Loader,
    messenger: Messenger,
    savedStateHandle: SavedStateHandle,
    private val navigator: Navigator,
    private val contentRepository: ContentRepository,
    private val sharer: Sharer<Content>,
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "YoutubeViewModel"
        private const val pageItemCount = 10
    }

    private var noMoreToLoad = false
    private var loadingSimilarContent = false
    private var currentPageNumber = 1

    private val _content = MutableStateFlow<Content?>(null)
    private val _similarContents = mutableStateListOf<Content>()

    val similarContents: List<Content> = _similarContents

    val content = _content.asStateFlow()

    init {
        initContent(savedStateHandle.get<String>(Destination.YouTube.routeArgName)!!)
    }

    fun selectSimilarContent(content: Content) {
        launchNetwork {
            contentRepository.fetchContentDetails(content.id)
                .collect {
                    _content.value = it
                    currentPageNumber = 1
                    noMoreToLoad = false
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun initContent(contentId: String) {
        launchNetwork(error = { loadingSimilarContent = false }) {
            contentRepository.fetchContentDetails(contentId)
                .catch {
                    navigator.navigateBack()
                    throw it
                }
                .flatMapLatest {
                    _content.value = it
                    loadingSimilarContent = true
                    contentRepository.fetchSimilarContents(
                        it.id,
                        currentPageNumber,
                        pageItemCount
                    )
                }
                .collect {
                    loadingSimilarContent = false
                    if (it.isEmpty()) {
                        noMoreToLoad = true
                    } else {
                        _similarContents.addAll(it)
                        currentPageNumber++
                    }
                }
        }
    }

    fun loadMoreSimilarContents() {
        val contentData = content.value ?: return
        if (loadingSimilarContent || noMoreToLoad) return

        launchNetwork(error = { loadingSimilarContent = false }) {
            contentRepository.fetchSimilarContents(
                contentData.id,
                currentPageNumber,
                pageItemCount
            )
                .collect {
                    loadingSimilarContent = false
                    if (it.isEmpty()) {
                        noMoreToLoad = true
                    } else {
                        _similarContents.addAll(it)
                        currentPageNumber++
                    }
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
                _content.value = content.copy(
                    liked = liked,
                    likes = if (liked) content.likes?.plus(1) else content.likes?.minus(1)
                )
            }
        }
    }

    fun shareContent(content: Content) {
        sharer.share(Payload.text(content))
    }

    fun shareWhatsappContent(content: Content) {
        sharer.share(Payload.whatsappText(content))
    }
}