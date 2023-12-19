package com.whereismymotivation.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereismymotivation.R
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.utils.log.Logger
import com.whereismymotivation.utils.network.NetworkError
import com.whereismymotivation.utils.network.NetworkHelper
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.net.ssl.HttpsURLConnection

abstract class BaseViewModel (
    private val networkHelper: NetworkHelper,
    private val loader: Loader,
    private val messenger: Messenger,
) :
    ViewModel() {

    companion object {
        const val TAG = "BaseViewModel"
    }

    protected fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    protected fun checkInternetConnectionWithMessage(): Boolean {
        val connected = networkHelper.isNetworkConnected()
        if (!connected) messenger.deliverRes(Message.error(R.string.no_internet_connection))
        return connected
    }

    protected fun launchNetwork(
        silent: Boolean = false,
        error: (NetworkError) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ) {
        if (!silent && checkInternetConnectionWithMessage()) {
            loader.start()
            viewModelScope.launch {
                try {
                    block()
                } catch (e: Throwable) {
                    if(e is CancellationException) return@launch
                    val networkError = handleNetworkError(e)
                    error(networkError)
                    Logger.d(TAG, e)
                    Logger.record(e)
                } finally {
                    loader.stop()
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    block()
                } catch (e: Throwable) {
                    val networkError = handleNetworkError(e)
                    error(networkError)
                    Logger.d(TAG, e)
                    Logger.record(e)
                } finally {
                    loader.stop()
                }
            }
        }
    }

    private fun handleNetworkError(e: Throwable): NetworkError {
        return networkHelper.castToNetworkError(e).apply {
            when (status) {
                0 -> messenger.deliverRes(Message.error(R.string.server_connection_error))

                HttpsURLConnection.HTTP_UNAUTHORIZED -> {
                    messenger.deliver(Message.error(message))
                }

                HttpsURLConnection.HTTP_FORBIDDEN -> {
                    messenger.deliverRes(Message.error(R.string.permission_not_available))
                }

                HttpsURLConnection.HTTP_BAD_REQUEST -> {
                    message.let { messenger.deliver(Message.error(message)) }
                }

                HttpsURLConnection.HTTP_NOT_FOUND -> {
                    message.let { messenger.deliver(Message.error(message)) }
                }

                HttpsURLConnection.HTTP_INTERNAL_ERROR ->
                    messenger.deliverRes(Message.error(R.string.network_internal_error))

                HttpsURLConnection.HTTP_UNAVAILABLE ->
                    messenger.deliverRes(Message.error(R.string.network_server_not_available))

                else -> {
                    messenger.deliverRes(Message.error(R.string.something_went_wrong))
                }
            }
        }

    }
}