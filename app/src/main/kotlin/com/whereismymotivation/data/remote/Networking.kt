package com.whereismymotivation.data.remote

import com.squareup.moshi.Moshi
import com.whereismymotivation.BuildConfig
import com.whereismymotivation.data.remote.interceptors.ImageHeaderInterceptor
import com.whereismymotivation.data.remote.interceptors.LocalHostInterceptor
import com.whereismymotivation.data.remote.interceptors.NetworkInterceptor
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

    fun <T> createService(baseUrl: String, client: OkHttpClient, service: Class<T>): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().build()
                )
            )
            .build()
            .create(service)


    fun createOkHttpClientForApis(
        networkInterceptor: NetworkInterceptor,
        headerInterceptor: RequestHeaderInterceptor,
        refreshTokenInterceptor: RefreshTokenInterceptor,
        cacheDir: File,
        cacheSize: Long
    ) = OkHttpClient.Builder()
        .cache(Cache(cacheDir, cacheSize))
        .addInterceptor(networkInterceptor)
        .addInterceptor(headerInterceptor)
        .addInterceptor(refreshTokenInterceptor)
        .addInterceptor(getHttpLoggingInterceptor())
        .readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .build()

    fun createOkHttpClientForRefreshToken(
        networkInterceptor: NetworkInterceptor,
        headerInterceptor: RequestHeaderInterceptor,
    ) = OkHttpClient.Builder()
        .addInterceptor(networkInterceptor)
        .addInterceptor(headerInterceptor)
        .addInterceptor(getHttpLoggingInterceptor())
        .readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .build()

    fun createOkHttpClientForImage(
        imageHeaderInterceptor: ImageHeaderInterceptor,
        localHostInterceptor: LocalHostInterceptor,
        cacheDir: File,
        cacheSize: Long,
    ) = OkHttpClient.Builder()
        .cache(Cache(cacheDir, cacheSize))
        .addInterceptor(localHostInterceptor)
        .addNetworkInterceptor(imageHeaderInterceptor)
        .addInterceptor(HttpLoggingInterceptor()
            .apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
                else HttpLoggingInterceptor.Level.NONE
            })
        .readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
        .build()

    private fun getHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
}