package com.whereismymotivation.ui.content

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.repository.ContentRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    loader: Loader,
    messenger: Messenger,
    savedStateHandle: SavedStateHandle,
    private val navigator: Navigator,
    private val contentRepository: ContentRepository
) : BaseViewModel(networkHelper, loader, messenger) {

    companion object {
        const val TAG = "ContentViewModel"
        private const val CONTENT_ID_SAVED_STATE_KEY = "contentId"
        private const val pageItemCount = 10
    }

    private var loadingSimilarContent = false
    private var currentPageNumber = 1
    private val _content = MutableStateFlow<Content?>(null)
    val similarContents = mutableStateListOf<Content>()

    val content = _content.asStateFlow()

    init {
        initContent(savedStateHandle.get<String>(CONTENT_ID_SAVED_STATE_KEY)!!)
    }

    fun selectSimilarContent(content: Content) {
        launchNetwork {
            contentRepository.fetchContentDetails(content.id)
                .collect {
                    _content.value = it
                    currentPageNumber = 1
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun initContent(contentId: String) {
        launchNetwork(error = { loadingSimilarContent = false }) {
            contentRepository.fetchContentDetails(contentId)
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
                    similarContents.addAll(it)
                    currentPageNumber++
                    loadingSimilarContent = false
                }
        }
    }

    fun loadMoreSimilarContents() {
        val contentData = content.value ?: return
        if (loadingSimilarContent) return

        launchNetwork(error = { loadingSimilarContent = false }) {
            contentRepository.fetchSimilarContents(
                contentData.id,
                currentPageNumber,
                pageItemCount
            )
                .collect {
                    similarContents.addAll(it)
                    currentPageNumber++
                    loadingSimilarContent = false
                }
        }

    }
}