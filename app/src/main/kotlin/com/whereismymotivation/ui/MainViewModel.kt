package com.whereismymotivation.ui

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.remote.utils.ForcedLogout
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val loader: Loader,
    val messenger: Messenger,
    val sharer: Sharer<Content>,
    val navigator: Navigator,
    forcedLogout: ForcedLogout,
    userRepository: UserRepository,
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

    fun storeMotivation(text: String?) {
        viewModelScope.launch {
            delay(1000)
            text?.run {
                if (text.isValidUrl()) {
                    // TODO
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