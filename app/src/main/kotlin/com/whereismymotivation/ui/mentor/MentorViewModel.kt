package com.whereismymotivation.ui.mentor

import androidx.lifecycle.SavedStateHandle
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.repository.MentorRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.browser.ContentBrowser
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MentorViewModel @Inject constructor(
    loader: Loader,
    messenger: Messenger,
    savedStateHandle: SavedStateHandle,
    private val navigator: Navigator,
    private val mentorRepository: MentorRepository,
    private val contentBrowser: ContentBrowser
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "MentorsViewModel"
    }

    init {
        val mentorId: String = savedStateHandle.get<String>(Destination.Mentor.routeArgName)!!
        loadMentor(mentorId)
        loadMentorContents(mentorId)
    }

    private val _mentor = MutableStateFlow<Mentor?>(null)
    private val _contents = MutableStateFlow<List<Content>?>(null)

    val mentor = _mentor.asStateFlow()
    val contents = _contents.asStateFlow()

    fun upPress() {
        navigator.navigateBack()
    }

    fun selectContent(content: Content) {
        contentBrowser.show(content)
    }

    private fun loadMentor(mentorId: String) {
        launchNetwork {
            mentorRepository.fetchMentorDetails(mentorId)
                .collect {
                    _mentor.value = it
                }
        }
    }

    private fun loadMentorContents(mentorId: String) {
        launchNetwork {
            mentorRepository.fetchMentorContents(mentorId, 1, 100)
                .collect {
                    _contents.value = it
                }
        }
    }
}