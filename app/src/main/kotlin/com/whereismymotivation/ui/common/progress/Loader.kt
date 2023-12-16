package com.whereismymotivation.ui.common.progress

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@ActivityRetainedScoped
class Loader @Inject constructor() {

    private val counter = AtomicInteger(0)

    private val _loading = MutableStateFlow(false)

    val loading = _loading.asStateFlow()

    fun start() =
        counter.incrementAndGet().run { _loading.value = true }

    fun stop() = counter.decrementAndGet().run {
        if (this == 0) _loading.value = false
    }
}

