package com.whereismymotivation.data.repository

import com.whereismymotivation.data.local.db.DatabaseService
import com.whereismymotivation.data.local.db.entity.Journal
import com.whereismymotivation.data.local.db.entity.Mood
import com.whereismymotivation.data.remote.NetworkService
import com.whereismymotivation.data.remote.request.JournalsRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JournalRepository @Inject constructor(
    private val databaseService: DatabaseService,
    private val networkService: NetworkService
) {

    fun fetchJournals(userId: String, pageNumber: Int, pageItemCount: Int) =
        databaseService.journalDao()
            .getPaginated(userId, (pageNumber - 1) * pageItemCount, pageItemCount)

    fun saveJournal(journal: Journal): Flow<Long> = flow {
        emit(databaseService.journalDao().insert(journal))
    }

    fun deleteJournal(journal: Journal): Flow<Int> = flow {
        emit(databaseService.journalDao().delete(journal))
    }

    fun sendJournal(journals: List<Journal>): Flow<String> = flow {
        emit(networkService.doJournalStorageCall(JournalsRequest(journals)))
    }.map { it.message }

    fun fetchUnSyncJournals(userId: String) =
        databaseService.journalDao().getAllUnSync(userId)

    fun markedSynced(journals: List<Mood>): Flow<Int> = flow {
        emit(databaseService.journalDao().setAsSynced(journals.map { it.id }))
    }
}