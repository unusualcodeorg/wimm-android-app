package com.whereismymotivation.ui.profile

import com.whereismymotivation.R
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.message.Message
import com.whereismymotivation.ui.message.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.NavTarget
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    private val userRepository: UserRepository,
    val navigator: Navigator,
    val messenger: Messenger
) : BaseViewModel(networkHelper, messenger) {

    companion object {
        const val TAG = "ProfileViewModel"
    }

    fun logout() {
        val exists = userRepository.userExists()
        if (exists) {
            launchNetwork {
                userRepository.logout()
                    .catch { handleNetworkError(it) }
                    .collect {
                        userRepository.removeCurrentUser()
                        navigator.navigateTo(NavTarget(Destination.Login, true))
                        messenger.deliver(Message.success("Logout Success"))
                    }
            }

        } else {
            messenger.deliverRes(Message.error(R.string.something_went_wrong))
        }
    }
}