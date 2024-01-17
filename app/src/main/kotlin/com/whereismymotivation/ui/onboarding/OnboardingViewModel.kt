package com.whereismymotivation.ui.onboarding

import androidx.compose.runtime.mutableStateListOf
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.data.repository.SubscriptionRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.NavTarget
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    loader: Loader,
    private val messenger: Messenger,
    private val navigator: Navigator,
    private val userRepository: UserRepository,
    private val subscriptionRepository: SubscriptionRepository
) : BaseViewModel(loader, messenger) {

    companion object {
        const val TAG = "OnboardingViewModel"
    }

    init {
        loadSuggestions()
    }

    val suggestionMentors = mutableStateListOf<Suggestion<Mentor>>()
    val suggestionTopics = mutableStateListOf<Suggestion<Topic>>()

    private fun loadSuggestions() {
        launchNetwork {
            combine(
                subscriptionRepository
                    .fetchRecommendedTopics(1, 50),
                subscriptionRepository
                    .fetchRecommendedMentors(1, 50),
            ) { topics, mentors ->
                suggestionTopics
                    .addAll(topics.map { Suggestion(it, false) })
                suggestionMentors
                    .addAll(mentors.map { Suggestion(it, false) })
            }.collect()
        }
    }

    fun topicSelect(suggestion: Suggestion<Topic>) {
        val index = suggestionTopics.indexOf((suggestion))
        if (index > -1) suggestionTopics[index] = suggestion.copy(selected = true)
    }

    fun topicUnselect(suggestion: Suggestion<Topic>) {
        val index = suggestionTopics.indexOf((suggestion))
        if (index > -1) suggestionTopics[index] = suggestion.copy(selected = false)

    }

    fun mentorSelect(suggestion: Suggestion<Mentor>) {
        val index = suggestionMentors.indexOf((suggestion))
        if (index > -1) suggestionMentors[index] = suggestion.copy(selected = true)
    }

    fun mentorUnselect(suggestion: Suggestion<Mentor>) {
        val index = suggestionMentors.indexOf((suggestion))
        if (index > -1) suggestionMentors[index] = suggestion.copy(selected = false)
    }

    fun complete() {
        val selectedMentors = suggestionMentors.filter { it.selected }
        val selectedTopic = suggestionTopics.filter { it.selected }
        if (selectedMentors.isEmpty() && selectedTopic.isEmpty()) {
            return messenger.deliverRes(Message.warning(R.string.pick_few_mentors_and_topics))
        }

        launchNetwork {
            subscriptionRepository.subscribe(
                suggestionMentors.map { it.data },
                selectedTopic.map { it.data }
            ).collect {
                messenger.deliver(Message.success(it))
                userRepository.markUserOnBoardingComplete()
                navigator.navigateTo(NavTarget(Destination.Home.route, true))
            }
        }

    }
}