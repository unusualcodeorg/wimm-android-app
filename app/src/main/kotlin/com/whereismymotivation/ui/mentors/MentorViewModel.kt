package com.whereismymotivation.ui.mentors

import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MentorViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    loader: Loader,
    messenger: Messenger
) : BaseViewModel(networkHelper, loader, messenger) {

    companion object {
        const val TAG = "MentorViewModel"
    }

    private val _mentors = MutableStateFlow<List<Mentor>>(emptyList())

    val mentors = _mentors.asStateFlow()

    fun selectMentor(mentor: Mentor) {}

}