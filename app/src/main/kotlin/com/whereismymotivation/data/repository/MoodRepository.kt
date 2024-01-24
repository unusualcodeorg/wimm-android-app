package com.whereismymotivation.data.repository

import com.whereismymotivation.data.local.db.DatabaseService
import com.whereismymotivation.data.local.db.entity.Mood
import com.whereismymotivation.data.remote.apis.user.UserApi
import com.whereismymotivation.data.remote.apis.user.request.MoodsRequest
import com.whereismymotivation.utils.coroutine.Dispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoodRepository @Inject constructor(
    private val userApi: UserApi,
    private val databaseService: DatabaseService,
    private val dispatcher: Dispatcher
) {

    suspend fun fetchMoods(userId: String): Flow<List<Mood>> =
        flow {
            emit(databaseService.moodDao().getAll(userId))
        }.flowOn(dispatcher.io())

    suspend fun fetchUnSyncMoods(userId: String): Flow<List<Mood>> =
        flow {
            emit(databaseService.moodDao().getAllUnSync(userId))
        }.flowOn(dispatcher.io())

    suspend fun saveMood(mood: Mood): Flow<Long> =
        flow {
            emit(databaseService.moodDao().insert(mood))
        }.flowOn(dispatcher.io())

    suspend fun sendMoods(moods: List<Mood>): Flow<String> =
        flow {
            emit(userApi.moodStorage(MoodsRequest(moods)))
        }.map { it.message }

    suspend fun markedSynced(moods: List<Mood>): Flow<Int> =
        flow {
            emit(databaseService.moodDao().setAsSynced(moods.map { it.id }))
        }.flowOn(dispatcher.io())
}