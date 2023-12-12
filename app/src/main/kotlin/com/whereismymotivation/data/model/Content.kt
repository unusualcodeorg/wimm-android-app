package com.whereismymotivation.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Content(

    @Json(name = "_id")
    val id: String,

    @Json(name = "category")
    val category: Category,

    @Json(name = "title")
    val title: String,

    @Json(name = "subtitle")
    val subtitle: String,

    @Json(name = "thumbnail")
    val thumbnail: String,

    @Json(name = "extra")
    val extra: String,

    @Json(name = "createdBy")
    val creator: User,

    @Json(name = "submit")
    var submit: Boolean? = null,

    @Json(name = "private")
    var private: Boolean? = null,

    @Json(name = "liked")
    var liked: Boolean? = null,

    @Json(name = "likes")
    var likes: Long? = null,

    @Json(name = "views")
    var views: Long? = null,

    @Json(name = "shares")
    var shares: Long? = null

) : Parcelable {
    @Keep
    enum class Category(val type: String) {
        AUDIO("AUDIO"),
        VIDEO("VIDEO"),
        IMAGE("IMAGE"),
        YOUTUBE("YOUTUBE"),
        FACEBOOK_VIDEO("FACEBOOK_VIDEO"),
        ARTICLE("ARTICLE"),
        QUOTE("QUOTE"),
        MENTOR_INFO("MENTOR_INFO"),
        TOPIC_INFO("TOPIC_INFO")
    }
}