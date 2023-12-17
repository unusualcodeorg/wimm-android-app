package com.whereismymotivation.ui.profile

import com.whereismymotivation.R
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.NavTarget
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    loader: Loader,
    private val userRepository: UserRepository,
    val navigator: Navigator,
    val messenger: Messenger
) : BaseViewModel(networkHelper, loader, messenger) {

    companion object {
        const val TAG = "ProfileViewModel"
    }

    fun logout() {
        val exists = userRepository.userExists()
        if (exists) {
            launchNetwork {
                userRepository.logout()
                    .collect {
                        userRepository.removeCurrentUser()
                        navigator.navigateTo(NavTarget(Destination.Login.route, true))
                        messenger.deliver(Message.success("Logout Success"))
                    }
            }
        } else {
            messenger.deliverRes(Message.error(R.string.something_went_wrong))
        }
    }
}