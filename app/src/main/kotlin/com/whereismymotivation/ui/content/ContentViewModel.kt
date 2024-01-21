package com.whereismymotivation.ui.content

import androidx.lifecycle.SavedStateHandle
import com.whereismymotivation.data.repository.ContentRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.browser.ContentBrowser
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    loader: Loader,
    messenger: Messenger,
    savedStateHandle: SavedStateHandle,
    private val navigator: Navigator,
    private val contentRepository: ContentRepository,
    private val contentBrowser: ContentBrowser
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "ContentViewModel"
    }

    val contentId = savedStateHandle.get<String>(Destination.YouTube.routeArgName)

    fun checkRedirection() {
        if (contentId == null) return navigator.navigateTo(Destination.Home.route)

        launchNetwork(error = { navigator.navigateTo(Destination.Home.route) }) {
            contentRepository.fetchContentDetails(contentId)
                .collect { contentBrowser.show(it, true) }
        }
    }
}