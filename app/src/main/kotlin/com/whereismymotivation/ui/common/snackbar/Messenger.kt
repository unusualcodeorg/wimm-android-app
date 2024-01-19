package com.whereismymotivation.ui.common.snackbar

import androidx.annotation.StringRes
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@ActivityRetainedScoped
class Messenger @Inject constructor() {
    private val _message =
        MutableSharedFlow<Message<String>>(extraBufferCapacity = 1)

    private val _messageRes =
        MutableSharedFlow<Message<@StringRes Int>>(extraBufferCapacity = 1)

    private val _clear =
        MutableSharedFlow<Boolean>(extraBufferCapacity = 1)

    private val _messageType = MutableStateFlow(Message.Type.SUCCESS) // Need initial state

    val message = _message.asSharedFlow()
    val messageRes = _messageRes.asSharedFlow()
    val clear = _clear.asSharedFlow()
    val messageType = _messageType.asStateFlow()


    fun deliver(message: Message<String>) {
        _clear.tryEmit(true)
        _messageType.tryEmit(message.type)
        _message.tryEmit(message)
    }

    fun deliverRes(message: Message<Int>) {
        _clear.tryEmit(true)
        _messageType.tryEmit(message.type)
        _messageRes.tryEmit(message)
    }
}