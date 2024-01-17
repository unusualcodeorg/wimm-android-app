package com.whereismymotivation.ui.splash

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.NavTarget
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    loader: Loader,
    firebaseRemote: FirebaseRemoteConfig,
    private val userRepository: UserRepository,
    val navigator: Navigator,
    val messenger: Messenger
) : BaseViewModel(loader, messenger) {

    companion object {
        const val TAG = "SplashViewModel"
    }

    init {
        // TODO: think of this for open source use case
        firebaseRemote.ensureInitialized().addOnCompleteListener {
            val exists = userRepository.userExists()
            if (exists) {
                if (userRepository.isOnBoardingComplete()) {
                    navigator.navigateTo(NavTarget(Destination.Home.Feed.route, true))
                } else {
                    navigator.navigateTo(NavTarget(Destination.Onboarding.route, true))
                }
            } else {
                navigator.navigateTo(NavTarget(Destination.Login.route, true))
            }
        }
    }
}