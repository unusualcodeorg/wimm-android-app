package com.whereismymotivation.data.repository

import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.remote.NetworkService
import com.whereismymotivation.utils.log.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MentorRepository @Inject constructor(
    private val networkService: NetworkService
) {

    fun fetchSubscriptionMentorList(): Flow<List<Mentor>> =
        flow {
            emit(networkService.subscriptionMentorList())
        }.map { it.data }


    fun fetchMentorContents(
        mentorId: String,
        pageNumber: Int,
        pageItemCount: Int
    ): Flow<List<Content>> = flow {
        emit(networkService.mentorContentList(mentorId, pageNumber, pageItemCount))
    }.map { it.data }

    fun fetchMentorDetails(mentorId: String): Flow<Mentor> = flow {
        Logger.d("ALI MentorRepository", Thread.currentThread().name)
        emit(networkService.mentorDetails(mentorId))
    }.map { it.data }
}