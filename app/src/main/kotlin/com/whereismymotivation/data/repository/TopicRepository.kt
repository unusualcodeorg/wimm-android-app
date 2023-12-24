package com.whereismymotivation.data.repository

import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.data.remote.NetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopicRepository @Inject constructor(
    private val networkService: NetworkService
) {

    fun fetchSubscriptionTopics(): Flow<List<Topic>> = flow {
        emit(networkService.subscriptionTopics())
    }.map {
        it.data.onEach { topic -> topic.subscribed = true }
    }

    fun fetchTopicContents(
        topicId: String,
        pageNumber: Int,
        pageItemCount: Int
    ): Flow<List<Content>> = flow {
        emit(networkService.topicContents(topicId, pageNumber, pageItemCount))
    }.map { it.data }

    fun fetchTopicDetails(topicId: String): Flow<Topic> = flow {
        emit(networkService.topicDetails(topicId))
    }.map { it.data }
}