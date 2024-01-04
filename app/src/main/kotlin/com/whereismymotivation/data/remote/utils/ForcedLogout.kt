package com.whereismymotivation.data.remote.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForcedLogout @Inject constructor() {

    private val _state = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)

    val state = _state.asSharedFlow()

    fun logout() {
        _state.tryEmit(true)
    }

}

