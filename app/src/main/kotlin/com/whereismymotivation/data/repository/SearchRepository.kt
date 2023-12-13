package com.whereismymotivation.data.repository

import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.UniversalSearchResult
import com.whereismymotivation.data.remote.NetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val networkService: NetworkService
) {

    fun fetchSearchResults(query: String): Flow<List<UniversalSearchResult>> = flow {
        emit(networkService.doSearchCall(query))
    }.map { it.data }

    fun fetchMentorSearchResults(query: String): Flow<List<UniversalSearchResult>> = flow {
        emit(networkService.doFilteredSearchCall(query, Content.Category.MENTOR_INFO.type))
    }.map { it.data }

    fun fetchTopicSearchResults(query: String): Flow<List<UniversalSearchResult>> = flow {
        emit(networkService.doFilteredSearchCall(query, Content.Category.TOPIC_INFO.type))
    }.map { it.data }
}
