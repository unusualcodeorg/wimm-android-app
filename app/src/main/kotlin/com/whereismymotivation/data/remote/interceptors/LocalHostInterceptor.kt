package com.whereismymotivation.data.remote.interceptors

import com.whereismymotivation.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.net.URI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalHostInterceptor @Inject constructor() :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        if (request.url.host == "localhost") {
            val baseUrl = URI(BuildConfig.BASE_URL)
            val url = request.url.newBuilder()
                .host(baseUrl.host)
                .port(baseUrl.port)
                .build()
            builder.url(url)
        }
        return chain.proceed(builder.build())
    }
}