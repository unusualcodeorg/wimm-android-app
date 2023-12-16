package com.whereismymotivation.ui.splash

import androidx.lifecycle.viewModelScope
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.NavTarget
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    loader: Loader,
    private val userRepository: UserRepository,
    val navigator: Navigator,
    val messenger: Messenger
) : BaseViewModel(networkHelper, loader, messenger) {

    companion object {
        const val TAG = "SplashViewModel"
    }

    fun animationComplete() {
        viewModelScope.launch {
            val exists = userRepository.userExists()
            if (exists) {
                navigator.navigateTo(NavTarget(Destination.Home.Feed, true))
            } else {
                navigator.navigateTo(NavTarget(Destination.Login, true))
            }
        }

    }
}