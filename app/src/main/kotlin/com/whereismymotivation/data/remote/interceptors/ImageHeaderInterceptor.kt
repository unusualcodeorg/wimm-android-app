package com.whereismymotivation.data.remote.interceptors

import com.whereismymotivation.BuildConfig
import com.whereismymotivation.data.remote.RequestHeaders
import okhttp3.Interceptor
import okhttp3.Response
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageHeaderInterceptor @Inject constructor(private val requestHeaders: RequestHeaders) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        if (URI(BuildConfig.BASE_URL).host == request.url.host) {
            val accessToken = requestHeaders.accessTokenFetcher.fetch()
            if (accessToken != null) {
                builder.addHeader(
                    RequestHeaders.Param.ACCESS_TOKEN.value,
                    "Bearer $accessToken"
                )
            }
            builder.addHeader(RequestHeaders.Param.API_KEY.value, requestHeaders.apiKey)
        }
        return chain.proceed(builder.build())
    }
}