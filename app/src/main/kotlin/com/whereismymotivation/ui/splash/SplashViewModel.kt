package com.whereismymotivation.ui.splash

import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.NavTarget
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    val navigator: Navigator,
) : BaseViewModel(networkHelper) {

    companion object {
        const val TAG = "SplashViewModel"
    }

    fun animationComplete() {
        navigator.navigateTo(NavTarget(Destination.Login, true))
    }
}