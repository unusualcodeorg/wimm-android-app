package com.whereismymotivation.ui.login

import androidx.lifecycle.viewModelScope
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.NavTarget
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    val navigator: Navigator,
) : BaseViewModel(networkHelper) {

    companion object {
        const val TAG = "LoginViewModel"
    }

    init {
        runDelayed(5000)
    }

    private fun runDelayed(millis: Long) {
        viewModelScope.launch {
            delay(millis)
            navigator.navigateTo(NavTarget(Destination.Home, true))
        }
    }
}