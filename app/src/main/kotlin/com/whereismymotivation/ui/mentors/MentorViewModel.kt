package com.whereismymotivation.ui.mentors

import androidx.lifecycle.viewModelScope
import com.whereismymotivation.data.local.file.LocalFiles
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.repository.MentorRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.utils.coroutine.Dispatcher
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MentorViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    loader: Loader,
    messenger: Messenger,
    private val dispatcher: Dispatcher,
    private val mentorRepository: MentorRepository,
    private val localFiles: LocalFiles
) : BaseViewModel(networkHelper, loader, messenger) {

    companion object {
        const val TAG = "MentorViewModel"
    }

    private val _mentors = MutableStateFlow<List<Mentor>>(emptyList())

    val mentors = _mentors.asStateFlow()

    init {
        loadMentors()
    }

    fun selectMentor(mentor: Mentor) {}

    private fun loadMentors() {
        launchNetwork {
            mentorRepository.fetchSubscriptionMentorList()
                .collect {
                    _mentors.value = it
                }
        }

        viewModelScope.launch {
            withContext(dispatcher.io()) {
                localFiles.getMentorsForSuggestion()
                    .collect {
                        _mentors.value = it
                    }
            }
        }
    }

}