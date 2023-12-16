package com.whereismymotivation.data.repository

import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.model.SubscriptionInfo
import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.data.remote.NetworkService
import com.whereismymotivation.data.remote.request.SubscriptionModifyRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SubscriptionRepository @Inject constructor(
    private val networkService: NetworkService,
) {

    fun fetchRecommendedMentorList(pageNumber: Int, pageItemCount: Int): Flow<List<Mentor>> = flow {
        emit(networkService.recommendedMentorList(pageNumber, pageItemCount))
    }.map { it.data }

    fun fetchRecommendedTopicList(pageNumber: Int, pageItemCount: Int): Flow<List<Topic>> = flow {
        emit(networkService.recommendedTopicList(pageNumber, pageItemCount))
    }.map { it.data }

    fun subscribe(mentors: List<Mentor>, topics: List<Topic>): Flow<String> = flow {
        val mentorsArray: Array<String> = Array(mentors.size) { "" }
        for (i in mentors.indices) mentorsArray[i] = mentors[i].id

        val topicsArray: Array<String> = Array(topics.size) { "" }
        for (i in topics.indices) topicsArray[i] = topics[i].id

        emit(
            networkService
                .subscriptionSubscribe(SubscriptionModifyRequest(mentorsArray, topicsArray))
        )
    }.map { it.message }

    fun unsubscribe(mentors: List<Mentor>, topics: List<Topic>): Flow<String> = flow {
        val mentorsArray: Array<String> = Array(mentors.size) { "" }
        for (i in mentors.indices) mentorsArray[i] = mentors[i].id

        val topicsArray: Array<String> = Array(topics.size) { "" }
        for (i in topics.indices) topicsArray[i] = topics[i].id

        emit(
            networkService
                .subscriptionUnsubscribe(SubscriptionModifyRequest(mentorsArray, topicsArray))
        )

    }.map { it.message }

    fun fetchSubscriptionInfoMentor(mentorId: String): Flow<SubscriptionInfo> = flow {
        emit(networkService.subscriptionInfoMentor(mentorId))
    }.map { it.data }

    fun fetchSubscriptionInfoTopic(topicId: String): Flow<SubscriptionInfo> = flow {
        emit(networkService.subscriptionInfoTopic(topicId))
    }.map { it.data }

}
