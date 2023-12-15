package com.whereismymotivation.ui.base

import androidx.lifecycle.ViewModel
import com.whereismymotivation.R
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.common.Resource
import com.whereismymotivation.utils.network.NetworkError
import com.whereismymotivation.utils.network.NetworkHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

open class BaseViewModel @Inject constructor(
    private val navigator: Navigator,
    private val networkHelper: NetworkHelper
) :
    ViewModel() {

    companion object {
        const val TAG = "HomeViewModel"
    }

    protected val _message = MutableStateFlow(Resource.unknown(""))
    val message = _message.asStateFlow()

    private val _networkMessage = MutableStateFlow(Resource.unknown(0))
    val networkMessage = _networkMessage.asStateFlow()

    protected fun handleNetworkError(err: Throwable?) =
        err?.let {
            return@let networkHelper.castToNetworkError(it).apply {
                when (status) {
                    0 -> _networkMessage.value = Resource.error(R.string.server_connection_error)
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> {
                        _networkMessage.value = Resource.error(R.string.network_login_again)
                    }

                    HttpsURLConnection.HTTP_FORBIDDEN -> {
                        _networkMessage.value = Resource.error(R.string.permission_not_available)
                    }

                    HttpsURLConnection.HTTP_BAD_REQUEST -> {
                        it.message?.let { _message.value = Resource.error(this.message) }
                    }

                    HttpsURLConnection.HTTP_NOT_FOUND -> {
                        _networkMessage.value = Resource.error(R.string.something_went_wrong)
                    }

                    HttpsURLConnection.HTTP_INTERNAL_ERROR ->
                        _networkMessage.value = Resource.error(R.string.network_internal_error)

                    HttpsURLConnection.HTTP_UNAVAILABLE ->
                        _networkMessage.value =
                            Resource.error(R.string.network_server_not_available)

                    else -> {
                        _networkMessage.value =
                            Resource.error(R.string.something_went_wrong)
                    }
                }
            }
        } ?: NetworkError()

}