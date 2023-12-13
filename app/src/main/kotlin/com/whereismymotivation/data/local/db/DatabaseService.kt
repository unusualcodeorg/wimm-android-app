package com.whereismymotivation.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.whereismymotivation.data.local.db.dao.JournalDao
import com.whereismymotivation.data.local.db.dao.MoodDao
import com.whereismymotivation.data.local.db.entity.Journal
import com.whereismymotivation.data.local.db.entity.Mood
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        Mood::class,
        Journal::class
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converter::class)
abstract class DatabaseService : RoomDatabase() {

    abstract fun moodDao(): MoodDao

    abstract fun journalDao(): JournalDao
}