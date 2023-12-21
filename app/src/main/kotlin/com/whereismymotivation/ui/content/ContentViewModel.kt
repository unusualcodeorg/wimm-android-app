package com.whereismymotivation.ui.content

import androidx.lifecycle.SavedStateHandle
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.repository.ContentRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    }

    private val contentId: String = savedStateHandle.get<String>(CONTENT_ID_SAVED_STATE_KEY)!!

    init {
        loadContent(contentId)
    }

    private val _content = MutableStateFlow<Content?>(null)

    val content = _content.asStateFlow()

    fun upPress() {
        navigator.navigateBack()
    }

    private fun loadContent(contentId: String) {
        launchNetwork {
            contentRepository.fetchContentDetails(contentId)
                .collect {
                    _content.value = it
                }
        }
    }

}