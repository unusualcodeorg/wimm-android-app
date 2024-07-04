package com.whereismymotivation.ui.profile

import androidx.lifecycle.SavedStateHandle
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
import kotlinx.coroutines.runBlocking
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
        ProfileTab.fromName(
            savedStateHandle.get<String>(Destination.Home.Profile.routeArgName)
                ?: ProfileTab.MOOD.name
        )
    )

    private val _user = MutableStateFlow(runBlocking { userRepository.mustGetCurrentUser() })

    val user = _user.asStateFlow()
    val selectedTab = _selectedTab.asStateFlow()

    fun selectTab(tab: ProfileTab) {
        _selectedTab.value = tab
    }

    fun logout() {
        launchNetwork {
            authRepository.logout()
                .collect {
                    userRepository.removeCurrentUser()
                    navigator.navigateTo(Destination.Login.route, true)
                    messenger.deliver(Message.success("Logout Success"))
                }
        }
    }
}