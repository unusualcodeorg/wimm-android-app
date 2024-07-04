package com.whereismymotivation.ui.journal

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.whereismymotivation.R
import com.whereismymotivation.data.local.db.entity.Journal
import com.whereismymotivation.data.repository.JournalRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.common.CalendarUtils
import com.whereismymotivation.utils.common.Null
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalsViewModel @Inject constructor(
    loader: Loader,
    navigator: Navigator,
    val userRepository: UserRepository,
    val messenger: Messenger,
    private val journalRepository: JournalRepository
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "JournalsViewModel"
    }

    private val pageItemCount = 10
    private var loading = false
    private var currentPageNumber = 1

    private val _journals = mutableStateListOf<Journal>()
    private val _journalText = MutableStateFlow("")

    val journals: List<Journal> = _journals
    val journalText = _journalText.asStateFlow()

    private fun loadJournals(pageNumber: Int) {
        if (loading) return
        loading = true
        viewModelScope.launch {
            val user = userRepository.mustGetCurrentUser()
            journalRepository
                .fetchJournals(user.id, pageNumber, pageItemCount)
                .catch {
                    loading = false
                    messenger.deliverRes(Message.error(R.string.something_went_wrong))
                }
                .collect {
                    if (it.isNotEmpty()) {
                        _journals.addAll(it)
                        currentPageNumber++
                        loading = false
                    }
                }
        }
    }

    fun loadMore() {
        loadJournals(currentPageNumber)
    }

    fun textChange(text: String) {
        _journalText.value = text
    }

    fun delete(journal: Journal) {
        viewModelScope.launch {
            journalRepository
                .deleteJournal(journal)
                .catch {
                    messenger.deliverRes(Message.error(R.string.something_went_wrong))
                }
                .collect {
                    _journals.remove(journal)
                    messenger.deliverRes(Message.success(R.string.journal_deletion_success))
                }
        }
    }

    fun record() {
        val text = _journalText.value
        if (text.isBlank())
            return messenger.deliverRes(Message.error(R.string.record_can_not_empty))

        viewModelScope.launch {
            val user = userRepository.mustGetCurrentUser()
            val now = CalendarUtils.now()
            val journal = Journal(0, String.Null(), text, user.id, now, now)
            journalRepository
                .saveJournal(journal)
                .catch {
                    messenger.deliverRes(Message.error(R.string.something_went_wrong))
                }
                .collect {
                    val create = journal.copy(id = it)
                    _journals.add(0, create)
                    _journalText.value = ""
                    messenger.deliverRes(Message.success(R.string.journal_recorded_message))
                }

        }
    }

    fun select(journal: Journal) {
        // TODO
    }
}