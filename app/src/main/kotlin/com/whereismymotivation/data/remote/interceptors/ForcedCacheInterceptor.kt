package com.whereismymotivation.data.remote.interceptors

import com.whereismymotivation.data.remote.utils.NetworkHelper
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ForcedCacheInterceptor @Inject constructor(private val networkHelper: NetworkHelper) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (!networkHelper.isNetworkConnected()) builder.cacheControl(CacheControl.FORCE_CACHE)
        return chain.proceed(builder.build())
    }
}