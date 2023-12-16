package com.whereismymotivation.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereismymotivation.R
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.utils.log.Logger
import com.whereismymotivation.utils.network.NetworkHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

open class BaseViewModel @Inject constructor(
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

    protected fun launchNetwork(silent: Boolean = false, block: suspend CoroutineScope.() -> Unit) {
        if (!silent && checkInternetConnectionWithMessage()) {
            loader.start()
            messenger.reset()
            viewModelScope.launch {
                try {
                    block()
                } catch (e: Throwable) {
                    handleNetworkError(e)
                    Logger.d(TAG, e)
                    Logger.record(e)
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    block()
                } catch (e: Throwable) {
                    Logger.d(TAG, e)
                    Logger.record(e)
                }
            }
        }
    }

    private fun handleNetworkError(e: Throwable) {
        return networkHelper.castToNetworkError(e).run {
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
                    messenger.deliverRes(Message.error(R.string.something_went_wrong))
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