package com.whereismymotivation.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.whereismymotivation.data.local.db.entity.Journal

@Dao
interface JournalDao {

    @Delete
    suspend fun delete(entity: Journal): Int
    //emits an int value, indicating the number of rows removed from the database.

    @Insert
    suspend fun insert(entity: Journal): Long
    // emits a long, which is the new rowId for the inserted item

    @Query("SELECT * FROM journals WHERE createdBy = :userId ORDER BY createdAt DESC")
    fun getAll(userId: String): List<Journal>

    @Query("SELECT * FROM journals WHERE createdBy = :userId ORDER BY createdAt DESC LIMIT :limit OFFSET :offset")
    fun getPaginated(userId: String, offset: Int, limit: Int): List<Journal>

    @Query("SELECT * FROM journals WHERE createdBy = :userId AND synced = 0 ORDER BY createdAt DESC")
    fun getAllUnSync(userId: String): List<Journal>

    @Query("UPDATE journals SET synced = 1 WHERE id IN (:ids)")
    suspend fun setAsSynced(ids: List<Long>): Int
}