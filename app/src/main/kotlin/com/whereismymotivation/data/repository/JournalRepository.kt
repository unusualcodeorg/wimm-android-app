package com.whereismymotivation.data.repository

import com.whereismymotivation.data.local.db.DatabaseService
import com.whereismymotivation.data.local.db.entity.Journal
import com.whereismymotivation.data.remote.apis.user.UserApi
import com.whereismymotivation.data.remote.apis.user.request.JournalsRequest
import com.whereismymotivation.utils.coroutine.Dispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JournalRepository @Inject constructor(
    private val databaseService: DatabaseService,
    private val userApi: UserApi,
    private val dispatcher: Dispatcher
) {

    suspend fun fetchJournals(
        userId: String,
        pageNumber: Int,
        pageItemCount: Int
    ): Flow<List<Journal>> =
        flow {
            emit(
                databaseService.journalDao()
                    .getPaginated(userId, (pageNumber - 1) * pageItemCount, pageItemCount)
            )
        }.flowOn(dispatcher.io())

    suspend fun saveJournal(journal: Journal): Flow<Long> =
        flow {
            emit(databaseService.journalDao().insert(journal))
        }.flowOn(dispatcher.io())

    suspend fun deleteJournal(journal: Journal): Flow<Int> =
        flow {
            emit(databaseService.journalDao().delete(journal))
        }.flowOn(dispatcher.io())

    suspend fun sendJournal(journals: List<Journal>): Flow<String> =
        flow {
            emit(userApi.journalStorage(JournalsRequest(journals)))
        }.map { it.message }

    suspend fun fetchUnSyncJournals(userId: String): Flow<List<Journal>> =
        flow {
            emit(databaseService.journalDao().getAllUnSync(userId))
        }.flowOn(dispatcher.io())

    suspend fun markedSynced(journals: List<Journal>): Flow<Int> =
        flow {
            emit(databaseService.journalDao().setAsSynced(journals.map { it.id }))
        }.flowOn(dispatcher.io())
}