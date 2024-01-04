package com.whereismymotivation.data.remote.interceptors

import com.whereismymotivation.data.remote.utils.NetworkHelper
import com.whereismymotivation.data.remote.utils.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkInterceptor @Inject constructor(
    private val networkHelper: NetworkHelper
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkHelper.isNetworkConnected())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }
}
