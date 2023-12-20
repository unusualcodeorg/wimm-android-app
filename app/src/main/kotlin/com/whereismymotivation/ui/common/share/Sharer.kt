package com.whereismymotivation.ui.common.share

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@ActivityRetainedScoped
class Sharer<T> @Inject constructor() {
    private val _payload =
        MutableSharedFlow<Payload<T>>(extraBufferCapacity = 1)

    val payload = _payload.asSharedFlow()

    fun share(payload: Payload<T>) {
        _payload.tryEmit(payload)
    }

}