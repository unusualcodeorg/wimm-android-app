package com.whereismymotivation.ui.navigation

import android.net.Uri
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@ActivityRetainedScoped
class Navigator @Inject constructor() {
    private val _navigate =
        MutableSharedFlow<NavTarget>(extraBufferCapacity = 1)

    private val _deeplink =
        MutableSharedFlow<NavDeeplink>(extraBufferCapacity = 1)

    private val _back =
        MutableSharedFlow<NavBack>(extraBufferCapacity = 1)

    private val _end =
        MutableSharedFlow<Boolean>(extraBufferCapacity = 1)

    val navigate = _navigate.asSharedFlow()
    val deeplink = _deeplink.asSharedFlow()
    val back = _back.asSharedFlow()
    val end = _end.asSharedFlow()

    fun navigateTo(route: String, popBackstack: Boolean = false) {
        _navigate.tryEmit(NavTarget(route, popBackstack))
    }

    fun navigateTo(uri: Uri, popBackstack: Boolean = false) {
        _deeplink.tryEmit(NavDeeplink(uri, popBackstack))
    }

    fun navigateBack(recreate: Boolean = false) {
        _back.tryEmit(NavBack(recreate))
    }

    fun finish() {
        _end.tryEmit(true)
    }

    data class NavTarget(val route: String, val popBackstack: Boolean = false)

    data class NavDeeplink(val uri: Uri, val popBackstack: Boolean = false)

    data class NavBack(val recreate: Boolean)

}
