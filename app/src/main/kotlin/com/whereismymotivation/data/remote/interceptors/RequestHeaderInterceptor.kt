package com.whereismymotivation.data.remote.interceptors

import com.whereismymotivation.data.remote.RequestHeaders
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestHeaderInterceptor @Inject constructor(private val requestHeaders: RequestHeaders) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val apiAuthType = request.header(RequestHeaders.Key.API_AUTH_TYPE)

        builder.removeHeader(RequestHeaders.Key.API_AUTH_TYPE)

        when (apiAuthType) {
            RequestHeaders.Type.PROTECTED.value -> {
                val accessToken = requestHeaders.accessTokenFetcher.fetch()
                if (accessToken != null) {
                    builder.addHeader(
                        RequestHeaders.Param.ACCESS_TOKEN.value,
                        "Bearer $accessToken"
                    )
                }
                builder.addHeader(RequestHeaders.Param.API_KEY.value, requestHeaders.apiKey)
            }

            RequestHeaders.Type.PUBLIC.value ->
                builder.addHeader(RequestHeaders.Param.API_KEY.value, requestHeaders.apiKey)
        }

        if (apiAuthType != null) {
            requestHeaders.deviceIdFetcher.fetch()?.let {
                builder.addHeader(RequestHeaders.Param.DEVICE_ID.value, it)
            }
            requestHeaders.appVersionCodeFetcher.fetch()?.toString()?.let {
                builder.addHeader(RequestHeaders.Param.ANDROID_VERSION.value, it)
            }
        }

        return chain.proceed(builder.build())
    }
}