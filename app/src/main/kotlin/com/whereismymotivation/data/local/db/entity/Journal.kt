package com.whereismymotivation.data.local.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "journals")
data class Journal(

    @Json(name = "id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @Json(name = "_id")
    @ColumnInfo(name = "_id")
    val _id: String,

    @Json(name = "text")
    @ColumnInfo(name = "text")
    val text: String,

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

) : Parcelable