package com.whereismymotivation.ui.topic

import androidx.lifecycle.SavedStateHandle
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.data.repository.TopicRepository
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
class TopicViewModel @Inject constructor(
    loader: Loader,
    messenger: Messenger,
    savedStateHandle: SavedStateHandle,
    private val navigator: Navigator,
    private val topicRepository: TopicRepository,
    private val contentBrowser: ContentBrowser
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "TopicViewModel"
    }

    init {
        val topicId: String = savedStateHandle.get<String>(Destination.Topic.routeArgName)!!
        loadTopic(topicId)
        loadTopicContents(topicId)
    }

    private val _topic = MutableStateFlow<Topic?>(null)
    private val _contents = MutableStateFlow<List<Content>?>(null)

    val topic = _topic.asStateFlow()
    val contents = _contents.asStateFlow()

    fun upPress() {
        navigator.navigateBack()
    }

    fun selectContent(content: Content) {
        contentBrowser.show(content)
    }

    private fun loadTopic(topicId: String) {
        launchNetwork {
            topicRepository.fetchTopicDetails(topicId)
                .collect {
                    _topic.value = it
                }
        }
    }

    private fun loadTopicContents(topicId: String) {
        launchNetwork {
            topicRepository.fetchTopicContents(topicId, 1, 100)
                .collect {
                    _contents.value = it
                }
        }
    }
}