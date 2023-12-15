package com.whereismymotivation.ui.splash

import androidx.lifecycle.viewModelScope
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    networkHelper: NetworkHelper
) : BaseViewModel(networkHelper) {

    companion object {
        const val TAG = "SplashViewModel"
    }

    private val _close = MutableStateFlow(false)
    val close = _close.asStateFlow()

    init {
        runDelayed(5000)
    }

    private fun runDelayed(millis: Long) {
        viewModelScope.launch {
            delay(millis)
            _close.value = true
        }
    }
}