package com.whereismymotivation.data.repository

import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.model.SubscriptionInfo
import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.data.remote.apis.subscription.SubscriptionApi
import com.whereismymotivation.data.remote.apis.subscription.request.SubscriptionModifyRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SubscriptionRepository @Inject constructor(
    private val subscriptionApi: SubscriptionApi,
) {

    suspend fun subscribe(mentors: List<Mentor>, topics: List<Topic>): Flow<String> =
        flow {
            val mentorsArray: Array<String> = Array(mentors.size) { "" }
            for (i in mentors.indices) mentorsArray[i] = mentors[i].id

            val topicsArray: Array<String> = Array(topics.size) { "" }
            for (i in topics.indices) topicsArray[i] = topics[i].id

            emit(
                subscriptionApi
                    .subscriptionSubscribe(SubscriptionModifyRequest(mentorsArray, topicsArray))
            )
        }.map { it.message }

    suspend fun unsubscribe(mentors: List<Mentor>, topics: List<Topic>): Flow<String> =
        flow {
            val mentorsArray: Array<String> = Array(mentors.size) { "" }
            for (i in mentors.indices) mentorsArray[i] = mentors[i].id

            val topicsArray: Array<String> = Array(topics.size) { "" }
            for (i in topics.indices) topicsArray[i] = topics[i].id

            emit(
                subscriptionApi
                    .subscriptionUnsubscribe(SubscriptionModifyRequest(mentorsArray, topicsArray))
            )

        }.map { it.message }

    suspend fun fetchSubscriptionInfoMentor(mentorId: String): Flow<SubscriptionInfo> =
        flow {
            emit(subscriptionApi.subscriptionInfoMentor(mentorId))
        }.map { it.data }

    suspend fun fetchSubscriptionInfoTopic(topicId: String): Flow<SubscriptionInfo> =
        flow {
            emit(subscriptionApi.subscriptionInfoTopic(topicId))
        }.map { it.data }

}
