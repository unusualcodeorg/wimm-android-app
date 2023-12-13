package com.whereismymotivation.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.whereismymotivation.data.local.db.entity.Mood

@Dao
interface MoodDao {

    @Delete
    suspend fun delete(entity: Mood): Int
    //emits an int value, indicating the number of rows removed from the database.

    @Insert
    suspend fun insert(entity: Mood): Long
    // emits a long, which is the new rowId for the inserted item

    @Query("SELECT * FROM moods WHERE createdBy = :userId ORDER BY createdAt DESC")
    fun getAll(userId: String): List<Mood>

    @Query("SELECT * FROM moods WHERE createdBy = :userId AND synced = 0 ORDER BY createdAt DESC")
    fun getAllUnSync(userId: String): List<Mood>

    @Query("UPDATE moods SET synced = 1 WHERE id IN (:ids)")
    suspend fun setAsSynced(ids: List<Long>): Int
}