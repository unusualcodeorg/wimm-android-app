package com.whereismymotivation.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.whereismymotivation.data.local.db.DatabaseService
import com.whereismymotivation.di.qualifier.DatabaseInfo
import com.whereismymotivation.di.qualifier.PrefsInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    @DatabaseInfo
    fun provideDatabaseName(): String = "wimm-db"

    @Provides
    @Singleton
    @PrefsInfo
    fun providePreferenceName(): String = "wimm-prefs"

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
        @PrefsInfo prefName: String
    ): SharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideDatabaseService(
        @ApplicationContext context: Context,
        @DatabaseInfo dbName: String
    ): DatabaseService = Room.databaseBuilder(
        context, DatabaseService::class.java,
        dbName
    ).build()

}