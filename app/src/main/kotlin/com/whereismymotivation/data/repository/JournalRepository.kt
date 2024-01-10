package com.whereismymotivation.data.repository

import com.whereismymotivation.data.local.db.DatabaseService
import com.whereismymotivation.data.local.db.entity.Journal
import com.whereismymotivation.data.remote.apis.user.UserApi
import com.whereismymotivation.data.remote.apis.user.request.JournalsRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JournalRepository @Inject constructor(
    private val databaseService: DatabaseService,
    private val userApi: UserApi
) {

    fun fetchJournals(userId: String, pageNumber: Int, pageItemCount: Int): Flow<List<Journal>> =
        flow {
            emit(
                databaseService.journalDao()
                    .getPaginated(userId, (pageNumber - 1) * pageItemCount, pageItemCount)
            )
        }

    fun saveJournal(journal: Journal): Flow<Long> = flow {
        emit(databaseService.journalDao().insert(journal))
    }

    fun deleteJournal(journal: Journal): Flow<Int> = flow {
        emit(databaseService.journalDao().delete(journal))
    }

    fun sendJournal(journals: List<Journal>): Flow<String> = flow {
        emit(userApi.journalStorage(JournalsRequest(journals)))
    }.map { it.message }

    fun fetchUnSyncJournals(userId: String): Flow<List<Journal>> = flow {
        emit(databaseService.journalDao().getAllUnSync(userId))
    }

    fun markedSynced(journals: List<Journal>): Flow<Int> = flow {
        emit(databaseService.journalDao().setAsSynced(journals.map { it.id }))
    }
}