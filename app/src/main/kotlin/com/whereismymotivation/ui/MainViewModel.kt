package com.whereismymotivation.ui

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.remote.utils.ForcedLogout
import com.whereismymotivation.data.repository.ContentRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.share.Sharer
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.common.isValidUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    forcedLogout: ForcedLogout,
    userRepository: UserRepository,
    val loader: Loader,
    val messenger: Messenger,
    val sharer: Sharer<Content>,
    val navigator: Navigator,
    private val contentRepository: ContentRepository,
) : BaseViewModel(loader, messenger, navigator) {

    init {
        viewModelScope.launch {
            forcedLogout.state
                .collect {
                    if (it) {
                        userRepository.removeCurrentUser()
                        navigator.navigateTo(Destination.Login.route, true)
                    }
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun storeInMyBox(url: String) {
        launchNetwork {
            contentRepository.fetchMetaContent(url)
                .flatMapLatest { contentRepository.createPrivateContent(it) }
                .collect {
                    navigator.navigateTo(Destination.Home.MyBox.route)
                }
        }
    }

    fun storeMotivation(text: String?) {
        viewModelScope.launch {
            text?.run {
                if (text.isValidUrl()) {
                    storeInMyBox(text)
                } else {
                    messenger.deliverRes(Message.error(R.string.not_valid_motivation_box_object))
                }
            } ?: messenger.deliverRes(Message.error(R.string.not_valid_motivation_box_object))
        }
    }

    fun handleDeepLink(uri: Uri?) {
        uri?.let { navigator.navigateTo(uri) }
    }
}