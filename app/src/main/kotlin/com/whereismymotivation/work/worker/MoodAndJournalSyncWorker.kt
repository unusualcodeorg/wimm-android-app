package com.whereismymotivation.work.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.whereismymotivation.analytics.Tracker
import com.whereismymotivation.data.repository.JournalRepository
import com.whereismymotivation.data.repository.MoodRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.utils.coroutine.Dispatcher
import com.whereismymotivation.utils.log.Logger
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@HiltWorker
class MoodAndJournalSyncWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val userRepository: UserRepository,
    private val journalRepository: JournalRepository,
    private val moodRepository: MoodRepository,
    private val tracker: Tracker,
    private val dispatcher: Dispatcher
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(dispatcher.io()) {
        val user = userRepository.getCurrentUser() ?: return@withContext Result.failure()

        awaitAll(async {
            moodRepository.fetchUnSyncMoods(user.id)
                .map { moods ->
                    if (moods.isNotEmpty())
                        moodRepository.sendMoods(moods)
                            .map { moodRepository.markedSynced(moods) }
                    else 0
                }
                .catch { Logger.record(it) }
                .collect {
                    tracker.trackMoodAndJournalSyncSuccess()
                }
        },
            async {
                journalRepository.fetchUnSyncJournals(user.id)
                    .map { journals ->
                        if (journals.isNotEmpty())
                            journalRepository.sendJournal(journals)
                                .map { journalRepository.markedSynced(journals) }
                        else 0
                    }
                    .catch { Logger.record(it) }
                    .collect {
                        tracker.trackMoodAndJournalSyncSuccess()
                    }
            })
        return@withContext Result.success()
    }
}