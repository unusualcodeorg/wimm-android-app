package com.whereismymotivation.data.repository

import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.UniversalSearchResult
import com.whereismymotivation.data.remote.apis.content.ContentApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val contentApi: ContentApi
) {

    suspend fun fetchSearchResults(query: String): Flow<List<UniversalSearchResult>> =
        flow {
            emit(contentApi.search(query))
        }.map { it.data }

    suspend fun fetchMentorSearchResults(query: String): Flow<List<UniversalSearchResult>> =
        flow {
            emit(contentApi.filteredSearch(query, Content.Category.MENTOR_INFO.type))
        }.map { it.data }

    suspend fun fetchTopicSearchResults(query: String): Flow<List<UniversalSearchResult>> =
        flow {
            emit(contentApi.filteredSearch(query, Content.Category.TOPIC_INFO.type))
        }.map { it.data }
}
