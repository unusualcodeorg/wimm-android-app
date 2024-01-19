package com.whereismymotivation.ui.mentors

import androidx.compose.runtime.mutableStateListOf
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.repository.MentorRepository
import com.whereismymotivation.data.repository.SubscriptionRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreMentorsViewModel @Inject constructor(
    loader: Loader,
    private val messenger: Messenger,
    private val navigator: Navigator,
    private val subscriptionRepository: SubscriptionRepository,
    private val mentorRepository: MentorRepository,
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "ExploreMentorsViewModel"
    }

    private val _mentors = mutableStateListOf<Mentor>()

    val mentors: List<Mentor> = _mentors

    private val pageItemCount = 20
    private var currentPageNumber = 1
    private var loading = false

    init {
        loadMentors(currentPageNumber)
    }

    private fun loadMentors(pageNumber: Int) {
        if (loading) return
        loading = true

        launchNetwork(error = { loading = false }) {
            mentorRepository
                .fetchRecommendedMentors(pageNumber, pageItemCount)
                .collect {
                    if (it.isNotEmpty()) {
                        _mentors.addAll(it.sortedBy { t -> t.subscribed == true })
                        currentPageNumber++
                        loading = false
                    }
                }
        }
    }

    fun loadMore() {
        loadMentors(currentPageNumber)
    }

    fun mentorSelect(mentor: Mentor) {
        val index = _mentors.indexOf((mentor))
        if (index > -1) _mentors[index] = mentor.copy(subscribed = true)
    }

    fun mentorUnselect(mentor: Mentor) {
        val index = _mentors.indexOf((mentor))
        if (index > -1) _mentors[index] = mentor.copy(subscribed = false)
    }

    fun complete() {
        val subscribedMentors = _mentors.filter { it.subscribed == true }

        if (subscribedMentors.isNotEmpty()) {
            loading = true
            launchNetwork(error = { loading = false }) {
                subscriptionRepository
                    .subscribe(subscribedMentors, emptyList())
                    .collect {
                        loading = false
                        messenger.deliver(Message.success(it))
                        navigator.navigateBack(true)
                    }
            }
        }
    }

    fun upPress() {
        navigator.navigateBack()
    }
}