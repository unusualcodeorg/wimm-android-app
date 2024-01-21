package com.whereismymotivation.ui.search

import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.data.repository.TopicRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SuggestionViewModel @Inject constructor(
    loader: Loader,
    messenger: Messenger,
    private val navigator: Navigator,
    private val topicRepository: TopicRepository
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "SuggestionViewModel"
    }

    init {
        loadTopics()
    }

    private val _topics = MutableStateFlow<List<Topic>>(emptyList())

    val topics = _topics.asStateFlow()

    private fun loadTopics() {
        launchNetwork {
            topicRepository.fetchRecommendedTopics(1, 10)
                .collect {
                    _topics.value = it
                }
        }
    }

    fun selectTopic(topic: Topic) {
        navigator.navigateTo(Destination.Topic.dynamicRoute(topic.id))
    }

}