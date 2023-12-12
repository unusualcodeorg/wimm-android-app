package com.whereismymotivation.data.local.file

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.utils.common.FileUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalFiles @Inject constructor(@ApplicationContext val context: Context) {

    suspend fun getTopicsForSuggestion(): List<Topic> =
        FileUtils.readRawFile(context, R.raw.default_topics_suggestion)
            .run {
                return@run Moshi.Builder().build().adapter<List<Topic>>(
                    Types.newParameterizedType(
                        List::class.java,
                        Topic::class.java
                    )
                ).fromJson(this)!!
            }

    suspend fun getMentorsForSuggestion(): List<Mentor> =
        FileUtils.readRawFile(context, R.raw.default_mentors_suggestion)
            .run {
                return@run Moshi.Builder().build().adapter<List<Mentor>>(
                    Types.newParameterizedType(
                        List::class.java,
                        Mentor::class.java
                    )
                ).fromJson(this)!!
            }
}