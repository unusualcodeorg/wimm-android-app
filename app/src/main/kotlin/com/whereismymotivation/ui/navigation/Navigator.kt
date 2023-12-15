package com.whereismymotivation.ui.navigation

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@ActivityRetainedScoped
class Navigator @Inject constructor() {
    private val _navigate =
        MutableSharedFlow<NavTarget>(extraBufferCapacity = 1)

    private val _back =
        MutableSharedFlow<Boolean>(extraBufferCapacity = 1)

    private val _end =
        MutableSharedFlow<Boolean>(extraBufferCapacity = 1)

    val navigate = _navigate.asSharedFlow()
    val back = _back.asSharedFlow()
    val end = _end.asSharedFlow()

    fun navigateTo(navTarget: NavTarget) {
        _navigate.tryEmit(navTarget)
    }

    fun navigateBack() {
        _back.tryEmit(true)
    }

    fun finish() {
        _end.tryEmit(true)
    }
}
