package com.whereismymotivation.di

import android.content.Context
import com.whereismymotivation.R
import com.whereismymotivation.data.local.prefs.AppMetricPreferences
import com.whereismymotivation.data.local.prefs.UserPreferences
import com.whereismymotivation.utils.common.ResultFetcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    @DeviceIdInfo
    fun provideDeviceId(userPreferences: UserPreferences): ResultFetcher<String> =
        object : ResultFetcher<String> {
            override fun fetch(): String? = userPreferences.getDeviceId()
        }

    @Provides
    @Singleton
    @AppVersionCodeInfo
    fun provideAppVersionCode(appMetricPreferences: AppMetricPreferences): ResultFetcher<Long> =
        object : ResultFetcher<Long> {
            override fun fetch(): Long = appMetricPreferences.getCurrentAppVersion()
        }

    @Provides
    @Singleton
    @GoogleClientId
    fun provideGoogleClientId(@ApplicationContext context: Context): String = ""
//        context.getString(R.string.default_web_client_id)


}