package com.whereismymotivation.utils.common

interface ResultCallback<T : Any> {
    suspend fun onResult(result: T)
}

interface ResultCallbackBlocking<T : Any> {
    fun onResult(result: T)
}