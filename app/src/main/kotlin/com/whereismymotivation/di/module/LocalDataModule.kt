package com.whereismymotivation.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.whereismymotivation.data.local.db.DatabaseService
import com.whereismymotivation.di.qualifier.DatabaseInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "wimm-prefs")

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    @DatabaseInfo
    fun provideDatabaseName(): String = "wimm-db"

    @Provides
    @Singleton
    fun provideDataStorePreferences(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.dataStore

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