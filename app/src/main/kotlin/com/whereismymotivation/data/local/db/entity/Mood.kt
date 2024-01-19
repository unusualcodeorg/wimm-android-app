package com.whereismymotivation.data.local.db.entity

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "moods")
data class Mood(

    @Json(ignore = true)
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @Json(name = "id")
    @ColumnInfo(name = "_id")
    val _id: String,

    @Json(name = "code")
    @ColumnInfo(name = "code")
    val code: Code,

    @Json(name = "createdBy")
    @ColumnInfo(name = "createdBy")
    val createdBy: String,

    @Json(name = "createdAt")
    @ColumnInfo(name = "createdAt")
    val createdAt: Date,

    @Json(name = "updatedAt")
    @ColumnInfo(name = "updatedAt")
    val updatedAt: Date,

    @Json(name = "synced")
    @ColumnInfo(name = "synced")
    val synced: Boolean = false

) : Parcelable {

    @Keep
    enum class Code(val type: String) {
        ANGRY("ANGRY"),
        SAD("SAD"),
        NORMAL("NORMAL"),
        HAPPY("HAPPY"),
        VERY_HAPPY("VERY_HAPPY")
    }

    fun getValue() = codeToValue(code)

    companion object {

        fun getCode(value: String?) = when (value) {
            Code.ANGRY.type -> Code.ANGRY
            Code.SAD.type -> Code.SAD
            Code.NORMAL.type -> Code.NORMAL
            Code.HAPPY.type -> Code.HAPPY
            Code.VERY_HAPPY.type -> Code.VERY_HAPPY
            else -> Code.NORMAL
        }

        fun codeToValue(code: Code) =
            when (code) {
                Code.ANGRY -> 1
                Code.SAD -> 2
                Code.NORMAL -> 3
                Code.HAPPY -> 4
                Code.VERY_HAPPY -> 5
            }
    }
}