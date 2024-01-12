package com.whereismymotivation.data.repository

import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.data.remote.apis.content.ContentApi
import com.whereismymotivation.data.remote.apis.subscription.SubscriptionApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TopicRepository @Inject constructor(
    private val subscriptionApi: SubscriptionApi,
    private val contentApi: ContentApi
) {

    fun fetchSubscriptionTopics(): Flow<List<Topic>> =
        flow {
            emit(subscriptionApi.subscriptionTopics())
        }.map {
            it.data
        }

    fun fetchTopicContents(
        topicId: String,
        pageNumber: Int,
        pageItemCount: Int
    ): Flow<List<Content>> =
        flow {
            emit(contentApi.topicContents(topicId, pageNumber, pageItemCount))
        }.map { it.data }

    fun fetchTopicDetails(topicId: String): Flow<Topic> =
        flow {
            emit(contentApi.topicDetails(topicId))
        }.map { it.data }
}