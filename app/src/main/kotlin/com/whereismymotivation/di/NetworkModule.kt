package com.whereismymotivation.di

import android.content.Context
import com.whereismymotivation.BuildConfig
import com.whereismymotivation.data.local.prefs.UserPreferences
import com.whereismymotivation.data.remote.NetworkService
import com.whereismymotivation.data.remote.Networking
import com.whereismymotivation.data.remote.interceptors.RefreshTokenInterceptor
import com.whereismymotivation.data.remote.interceptors.RequestHeaderInterceptor
import com.whereismymotivation.utils.common.ResultCallback
import com.whereismymotivation.utils.common.ResultFetcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkService(
        @ApplicationContext context: Context,
        @BaseUrl baseUrl: String,
        requestHeaderInterceptor: RequestHeaderInterceptor,
        refreshTokenInterceptor: RefreshTokenInterceptor
    ): NetworkService =
        Networking.create(
            baseUrl, requestHeaderInterceptor, refreshTokenInterceptor,
            context.cacheDir, 10 * 1024 * 1024 // 10MB
        )

    @Provides
    @Singleton
    @ApiKeyInfo
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    @AccessTokenInfo
    fun provideAccessToken(userPreferences: UserPreferences): ResultFetcher<String> =
        object : ResultFetcher<String> {
            override fun fetch(): String? = userPreferences.getAccessToken()
        }

    @Provides
    @Singleton
    @RefreshTokenInfo
    fun provideRefreshToken(userPreferences: UserPreferences): ResultFetcher<String> =
        object : ResultFetcher<String> {
            override fun fetch(): String? = userPreferences.getRefreshToken()
        }

    @Provides
    @Singleton
    @AccessTokenInfo
    fun provideAccessTokenSaveLambda(userPreferences: UserPreferences): ResultCallback<String> =
        object : ResultCallback<String> {
            override fun onResult(result: String) = userPreferences.setAccessToken(result)
        }

    @Provides
    @Singleton
    @RefreshTokenInfo
    fun provideRefreshTokenSaveLambda(userPreferences: UserPreferences): ResultCallback<String> =
        object : ResultCallback<String> {
            override fun onResult(result: String) = userPreferences.setRefreshToken(result)
        }


}