package com.whereismymotivation.utils.common

interface ResultCallback<T : Any> {
    fun onResult(result: T)
}