package com.whereismymotivation.utils.coroutine

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * It will help in testing
 */
@Singleton
class Dispatcher @Inject constructor() {
    fun io() = Dispatchers.IO
    fun default() = Dispatchers.Default
    fun main() = Dispatchers.Main
    fun unconfined() = Dispatchers.Unconfined
}