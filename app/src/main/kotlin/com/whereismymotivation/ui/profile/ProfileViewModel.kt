package com.whereismymotivation.ui.profile

import com.whereismymotivation.R
import com.whereismymotivation.data.repository.AuthRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.NavTarget
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    loader: Loader,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    val navigator: Navigator,
    val messenger: Messenger
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "ProfileViewModel"
    }

    fun logout() {
        val exists = userRepository.userExists()
        if (exists) {
            launchNetwork {
                authRepository.logout()
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