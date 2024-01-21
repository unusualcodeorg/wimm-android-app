package com.whereismymotivation.ui.profile

import androidx.lifecycle.SavedStateHandle
import com.whereismymotivation.R
import com.whereismymotivation.data.repository.AuthRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    loader: Loader,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    val navigator: Navigator,
    val messenger: Messenger
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "ProfileViewModel"
    }

    private var _selectedTab = MutableStateFlow(
        ProfileTab.getByName(
            savedStateHandle.get<String>(Destination.ARG_NAME_PROFILE_TAB) ?: ProfileTab.MOOD.name
        )
    )

    private val _user = MutableStateFlow(userRepository.getCurrentUser()!!)

    val user = _user.asStateFlow()
    val selectedTab = _selectedTab.asStateFlow()

    fun selectTab(tab: ProfileTab) {
        _selectedTab.value = tab
    }

    fun logout() {
        val exists = userRepository.userExists()
        if (exists) {
            launchNetwork {
                authRepository.logout()
                    .collect {
                        userRepository.removeCurrentUser()
                        navigator.navigateTo(Destination.Login.route, true)
                        messenger.deliver(Message.success("Logout Success"))
                    }
            }
        } else {
            messenger.deliverRes(Message.error(R.string.something_went_wrong))
        }
    }
}