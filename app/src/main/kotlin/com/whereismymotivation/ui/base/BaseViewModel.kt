package com.whereismymotivation.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whereismymotivation.R
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.utils.network.NetworkError
import com.whereismymotivation.utils.network.NetworkHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

open class BaseViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val messenger: Messenger
) :
    ViewModel() {

    companion object {
        const val TAG = "HomeViewModel"
    }

    protected fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    protected fun checkInternetConnectionWithMessage(): Boolean {
        val connected = networkHelper.isNetworkConnected()
        if (!connected) messenger.deliverRes(Message.error(R.string.no_internet_connection))
        return connected
    }

    protected fun launchNetwork(block: suspend CoroutineScope.() -> Unit) {
        if (checkInternetConnectionWithMessage()) {
            messenger.reset()
            viewModelScope.launch { block() }
        }
    }

    protected fun handleNetworkError(err: Throwable?) =
        err?.let {
            return@let networkHelper.castToNetworkError(it).apply {
                when (status) {
                    0 -> messenger.deliverRes(Message.error(R.string.server_connection_error))
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> {
                        if (it.message != null)
                            messenger.deliver(Message.error(this.message))
                        else
                            messenger.deliverRes(Message.error(R.string.network_login_again))
                    }

                    HttpsURLConnection.HTTP_FORBIDDEN -> {
                        messenger.deliverRes(Message.error(R.string.permission_not_available))
                    }

                    HttpsURLConnection.HTTP_BAD_REQUEST -> {
                        it.message?.let { messenger.deliver(Message.error(this.message)) }
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
        } ?: NetworkError()

}