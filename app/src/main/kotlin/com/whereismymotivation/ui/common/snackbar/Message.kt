package com.whereismymotivation.ui.common.snackbar

import androidx.annotation.Keep
import androidx.annotation.StringRes

data class Message<out T> private constructor(val type: Type, val content: T) {

    companion object {
        fun success(content: String) = Message(Type.SUCCESS, content)

        fun success(@StringRes content: Int) = Message(Type.SUCCESS, content)

        fun error(content: String) = Message(Type.ERROR, content)

        fun error(@StringRes content: Int) = Message(Type.ERROR, content)

        fun warning(content: String) = Message(Type.WARNING, content)

        fun warning(@StringRes content: Int) = Message(Type.WARNING, content)

        fun info(content: String) = Message(Type.INFO, content)

        fun info(@StringRes content: Int) = Message(Type.INFO, content)

    }

    @Keep
    enum class Type {
        SUCCESS,
        ERROR,
        WARNING,
        INFO
    }
}