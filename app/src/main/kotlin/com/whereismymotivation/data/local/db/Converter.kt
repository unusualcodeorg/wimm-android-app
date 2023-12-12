package com.whereismymotivation.data.local.db

import androidx.room.TypeConverter
import com.whereismymotivation.data.local.db.entity.Mood
import com.whereismymotivation.utils.common.Constants
import java.util.*

class Converter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date = value?.let { Date(it) } ?: Date()

    @TypeConverter
    fun fromDate(date: Date?): Long = date?.time ?: Constants.NULL

    @TypeConverter
    fun fromMoodCode(code: Mood.Code?): String = code?.type ?: Mood.Code.NORMAL.type

    @TypeConverter
    fun fromMoodCodeString(value: String?): Mood.Code = Mood.getCode(value)
}