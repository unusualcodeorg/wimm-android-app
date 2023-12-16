package com.whereismymotivation.ui.profile

import androidx.lifecycle.viewModelScope
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.NavTarget
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    private val userRepository: UserRepository,
    val navigator: Navigator,
) : BaseViewModel(networkHelper) {

    companion object {
        const val TAG = "ProfileViewModel"
    }

    fun logout() {
        viewModelScope.launch {
            val exists = userRepository.userExists()
            if (exists) {
                viewModelScope.launch {
                    userRepository.logout()
                        .catch { handleNetworkError(it) }
                        .collect {
                            userRepository.removeCurrentUser()
                            navigator.navigateTo(NavTarget(Destination.Login, true))
                        }
                }
            } else {
                TODO()
            }
        }
    }
}