package com.whereismymotivation.data.repository

import com.whereismymotivation.data.local.db.DatabaseService
import com.whereismymotivation.data.local.db.entity.Journal
import com.whereismymotivation.data.local.db.entity.Mood
import com.whereismymotivation.data.remote.NetworkService
import com.whereismymotivation.data.remote.request.MoodsRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoodRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun fetchMoods(userId: String): Flow<List<Mood>> = flow {
        emit(databaseService.moodDao().getAll(userId))
    }

    fun fetchUnSyncMoods(userId: String): Flow<List<Mood>> = flow {
        emit(databaseService.moodDao().getAllUnSync(userId))
    }

    fun saveMood(mood: Mood): Flow<Long> = flow {
        emit(databaseService.moodDao().insert(mood))
    }

    fun sendMoods(moods: List<Mood>): Flow<String> = flow {
        emit(networkService.doMoodStorageCall(MoodsRequest(moods)))
    }.map { it.message }

    fun markedSynced(moods: List<Mood>): Flow<Int> = flow {
        emit(databaseService.moodDao().setAsSynced(moods.map { it.id }))
    }
}