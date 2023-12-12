package com.whereismymotivation.utils.common

interface ResultFetcher<T : Any> {
    fun fetch(): T?
}