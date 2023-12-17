package com.whereismymotivation.ui.mentor

import androidx.lifecycle.SavedStateHandle
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MentorViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    loader: Loader,
    messenger: Messenger,
    savedStateHandle: SavedStateHandle,
    private val navigator: Navigator
) : BaseViewModel(networkHelper, loader, messenger) {

    companion object {
        const val TAG = "MentorsViewModel"
        private const val MENTOR_ID_SAVED_STATE_KEY = "mentorId"
    }

    private val mentorId: String = savedStateHandle.get<String>(MENTOR_ID_SAVED_STATE_KEY)!!

    init {
        loadMentor(mentorId)
    }

    private val _mentor = MutableStateFlow<Mentor?>(null)

    val mentor = _mentor.asStateFlow()

    fun upPress() {
        navigator.navigateBack()
    }

    private fun loadMentor(mentorId: String) {

    }

    fun selectMentor(mentor: Mentor) {}
}