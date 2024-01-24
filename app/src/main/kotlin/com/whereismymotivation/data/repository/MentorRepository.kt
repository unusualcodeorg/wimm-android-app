package com.whereismymotivation.data.repository

import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.remote.apis.content.ContentApi
import com.whereismymotivation.data.remote.apis.mentor.MentorApi
import com.whereismymotivation.data.remote.apis.subscription.SubscriptionApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MentorRepository @Inject constructor(
    private val mentorApi: MentorApi,
    private val contentApi: ContentApi,
    private val subscriptionApi: SubscriptionApi
) {

    suspend fun fetchRecommendedMentors(
        pageNumber: Int,
        pageItemCount: Int
    ): Flow<List<Mentor>> =
        flow {
            emit(subscriptionApi.recommendedMentors(pageNumber, pageItemCount))
        }.map { it.data }

    suspend fun fetchSubscriptionMentors(): Flow<List<Mentor>> =
        flow {
            emit(subscriptionApi.subscriptionMentors())
        }.map { it.data }

    suspend fun fetchMentorContents(
        mentorId: String,
        pageNumber: Int,
        pageItemCount: Int
    ): Flow<List<Content>> =
        flow {
            emit(contentApi.mentorContents(mentorId, pageNumber, pageItemCount))
        }.map { it.data }

    suspend fun fetchMentorDetails(mentorId: String): Flow<Mentor> =
        flow {
            emit(mentorApi.mentorDetails(mentorId))
        }.map { it.data }
}