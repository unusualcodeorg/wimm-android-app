package com.whereismymotivation.di.module

import android.content.Context
import androidx.work.WorkManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.whereismymotivation.BuildConfig
import com.whereismymotivation.analytics.FirebaseTrackingClient
import com.whereismymotivation.analytics.TrackingClient
import com.whereismymotivation.data.local.prefs.AppMetricPreferences
import com.whereismymotivation.data.local.prefs.UserPreferences
import com.whereismymotivation.di.qualifier.AppVersionCodeInfo
import com.whereismymotivation.di.qualifier.DeviceIdInfo
import com.whereismymotivation.utils.common.ResultFetcherBlocking
import com.whereismymotivation.utils.config.RemoteKey
import com.whereismymotivation.utils.log.Logger
import com.whereismymotivation.work.AppWorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    @DeviceIdInfo
    fun provideDeviceId(
        userPreferences: UserPreferences
    ): ResultFetcherBlocking<String> = object : ResultFetcherBlocking<String> {
        override fun fetch(): String? = runBlocking { userPreferences.getDeviceId() }
    }

    @Provides
    @Singleton
    @AppVersionCodeInfo
    fun provideAppVersionCode(
        appMetricPreferences: AppMetricPreferences
    ): ResultFetcherBlocking<Long> = object : ResultFetcherBlocking<Long> {
        override fun fetch(): Long = runBlocking { appMetricPreferences.getCurrentAppVersion() }
    }

    @Provides
    @Singleton
    fun provideAnalyticsTrackingClient(
        @ApplicationContext context: Context
    ): TrackingClient = FirebaseTrackingClient(FirebaseAnalytics.getInstance(context))

    @Provides
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(remoteConfigSettings {
            minimumFetchIntervalInSeconds = when {
                BuildConfig.DEBUG -> 0
                else -> 60 * 60
            }
        })
        remoteConfig.setDefaultsAsync(RemoteKey.defaults)
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Logger.d(
                    "ApplicationModule",
                    "FirebaseRemoteConfig addOnCompleteListener isSuccessful updated: ${task.result}"
                )
            } else {
                Logger.e(
                    "ApplicationModule",
                    "FirebaseRemoteConfig addOnCompleteListener Not Successful"
                )
            }
        }
        return remoteConfig
    }

    @Provides
    @Singleton
    fun provideAppWorkManager(@ApplicationContext context: Context): AppWorkManager =
        AppWorkManager(WorkManager.getInstance(context))
}