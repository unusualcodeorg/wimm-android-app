package com.whereismymotivation.utils.common

interface Communicator<T : Any, E : Exception> {

    fun onResult(result: T)

    fun onError(error: E)
}