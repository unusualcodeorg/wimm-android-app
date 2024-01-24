package com.whereismymotivation.data.local.file

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.utils.common.FileUtils
import com.whereismymotivation.utils.log.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalFiles @Inject constructor(@ApplicationContext val context: Context) {

    suspend fun getTopicsForSuggestion(): Flow<List<Topic>> =
        flow {
            val data = FileUtils.readRawFile(context, R.raw.default_topics_suggestion)
                .run {
                    return@run Moshi.Builder().build().adapter<List<Topic>>(
                        Types.newParameterizedType(
                            List::class.java,
                            Topic::class.java
                        )
                    ).fromJson(this) ?: emptyList<Topic>()
                }
            emit(data)
        }.catch { Logger.record(it) }


    suspend fun getMentorsForSuggestion(): Flow<List<Mentor>> =
        flow {
            val data = FileUtils.readRawFile(context, R.raw.default_mentors_suggestion)
                .run {
                    return@run Moshi.Builder().build().adapter<List<Mentor>>(
                        Types.newParameterizedType(
                            List::class.java,
                            Mentor::class.java
                        )
                    ).fromJson(this) ?: emptyList<Mentor>()
                }
            emit(data)
        }.catch { Logger.record(it) }

}