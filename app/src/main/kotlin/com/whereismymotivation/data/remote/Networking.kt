package com.whereismymotivation.data.remote

import com.squareup.moshi.Moshi
import com.whereismymotivation.BuildConfig
import com.whereismymotivation.data.remote.interceptors.RefreshTokenInterceptor
import com.whereismymotivation.data.remote.interceptors.RequestHeaderInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object Networking {

    private const val NETWORK_CALL_TIMEOUT = 60

    fun create(
        baseUrl: String,
        headerInterceptor: RequestHeaderInterceptor,
        refreshTokenInterceptor: RefreshTokenInterceptor,
        cacheDir: File,
        cacheSize: Long
    ): NetworkService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(
            OkHttpClient.Builder()
                .cache(Cache(cacheDir, cacheSize))
                .addInterceptor(headerInterceptor)
                .addInterceptor(refreshTokenInterceptor)
                .addInterceptor(HttpLoggingInterceptor()
                    .apply {
                        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                    })
                .readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().build()
            )
        )
        .build()
        .create(NetworkService::class.java)
}