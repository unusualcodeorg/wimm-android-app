package com.whereismymotivation.di.module

import android.content.Context
import coil.ImageLoader
import com.whereismymotivation.BuildConfig
import com.whereismymotivation.data.local.prefs.UserPreferences
import com.whereismymotivation.data.remote.Networking
import com.whereismymotivation.data.remote.apis.auth.AuthApi
import com.whereismymotivation.data.remote.apis.auth.RefreshTokenApi
import com.whereismymotivation.data.remote.apis.content.ContentApi
import com.whereismymotivation.data.remote.apis.mentor.MentorApi
import com.whereismymotivation.data.remote.apis.subscription.SubscriptionApi
import com.whereismymotivation.data.remote.apis.topic.TopicApi
import com.whereismymotivation.data.remote.apis.user.UserApi
import com.whereismymotivation.data.remote.interceptors.ImageHeaderInterceptor
import com.whereismymotivation.data.remote.interceptors.LocalHostInterceptor
import com.whereismymotivation.data.remote.interceptors.NetworkInterceptor
import com.whereismymotivation.data.remote.interceptors.RefreshTokenInterceptor
import com.whereismymotivation.data.remote.interceptors.RequestHeaderInterceptor
import com.whereismymotivation.di.qualifier.AccessTokenInfo
import com.whereismymotivation.di.qualifier.ApiKeyInfo
import com.whereismymotivation.di.qualifier.BaseUrl
import com.whereismymotivation.di.qualifier.RefreshTokenInfo
import com.whereismymotivation.utils.common.ResultCallbackBlocking
import com.whereismymotivation.utils.common.ResultFetcherBlocking
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRefreshTokenApi(
        @BaseUrl baseUrl: String,
        networkInterceptor: NetworkInterceptor,
        requestHeaderInterceptor: RequestHeaderInterceptor,
    ): RefreshTokenApi = Networking.createService(
        baseUrl,
        Networking.createOkHttpClientForRefreshToken(
            networkInterceptor,
            requestHeaderInterceptor,
        ),
        RefreshTokenApi::class.java
    )

    @Provides
    @Singleton
    fun provideApiOkHttpClient(
        @ApplicationContext context: Context,
        networkInterceptor: NetworkInterceptor,
        requestHeaderInterceptor: RequestHeaderInterceptor,
        refreshTokenInterceptor: RefreshTokenInterceptor
    ): OkHttpClient = Networking.createOkHttpClientForApis(
        networkInterceptor,
        requestHeaderInterceptor,
        refreshTokenInterceptor,
        context.cacheDir,
        50 * 1024 * 1024 // 50MB
    )

    @Provides
    @Singleton
    fun provideAuthApi(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient
    ): AuthApi = Networking.createService(
        baseUrl,
        okHttpClient,
        AuthApi::class.java
    )

    @Provides
    @Singleton
    fun provideSubscriptionApi(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient
    ): SubscriptionApi = Networking.createService(
        baseUrl,
        okHttpClient,
        SubscriptionApi::class.java
    )

    @Provides
    @Singleton
    fun provideContentApi(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient
    ): ContentApi = Networking.createService(
        baseUrl,
        okHttpClient,
        ContentApi::class.java
    )

    @Provides
    @Singleton
    fun provideMentorApi(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient
    ): MentorApi = Networking.createService(
        baseUrl,
        okHttpClient,
        MentorApi::class.java
    )

    @Provides
    @Singleton
    fun provideTopicApi(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient
    ): TopicApi = Networking.createService(
        baseUrl,
        okHttpClient,
        TopicApi::class.java
    )

    @Provides
    @Singleton
    fun provideUserApi(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient
    ): UserApi = Networking.createService(
        baseUrl,
        okHttpClient,
        UserApi::class.java
    )

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        imageHeaderInterceptor: ImageHeaderInterceptor,
        localHostInterceptor: LocalHostInterceptor
    ): ImageLoader = ImageLoader.Builder(context)
        .okHttpClient(
            Networking.createOkHttpClientForImage(
                imageHeaderInterceptor,
                localHostInterceptor,
                context.cacheDir,
                50 * 1024 * 1024 // 50MB
            )
        )
        .build()

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
    fun provideAccessToken(
        userPreferences: UserPreferences
    ): ResultFetcherBlocking<String> = object : ResultFetcherBlocking<String> {
        override fun fetch(): String? = runBlocking { userPreferences.getAccessToken() }
    }

    @Provides
    @Singleton
    @RefreshTokenInfo
    fun provideRefreshToken(
        userPreferences: UserPreferences
    ): ResultFetcherBlocking<String> = object : ResultFetcherBlocking<String> {
        override fun fetch(): String? = runBlocking { userPreferences.getRefreshToken() }
    }

    @Provides
    @Singleton
    @AccessTokenInfo
    fun provideAccessTokenSaveLambda(
        userPreferences: UserPreferences
    ): ResultCallbackBlocking<String> = object : ResultCallbackBlocking<String> {
        override fun onResult(result: String) =
            runBlocking { userPreferences.setAccessToken(result) }
    }

    @Provides
    @Singleton
    @RefreshTokenInfo
    fun provideRefreshTokenSaveLambda(
        userPreferences: UserPreferences
    ): ResultCallbackBlocking<String> = object : ResultCallbackBlocking<String> {
        override fun onResult(result: String) =
            runBlocking { userPreferences.setRefreshToken(result) }
    }
}