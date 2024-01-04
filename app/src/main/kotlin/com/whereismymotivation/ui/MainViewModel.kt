package com.whereismymotivation.ui

import androidx.lifecycle.viewModelScope
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.remote.utils.ForcedLogout
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.share.Sharer
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.NavTarget
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val networkHelper: NetworkHelper,
    val loader: Loader,
    val messenger: Messenger,
    val sharer: Sharer<Content>,
    val navigator: Navigator,
    forcedLogout: ForcedLogout,
    userRepository: UserRepository,
) : BaseViewModel(
    networkHelper, loader, messenger
) {

    init {
        viewModelScope.launch {
            forcedLogout.state
                .collect {
                    if (it) {
                        userRepository.removeCurrentUser()
                        navigator.navigateTo(NavTarget(Destination.Login.route, true))
                    }
                }
        }
    }
}