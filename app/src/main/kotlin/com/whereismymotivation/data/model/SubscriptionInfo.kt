package com.whereismymotivation.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SubscriptionInfo(

    @Json(name = "itemId")
    val itemId: String,

    @Json(name = "contentType")
    val category: Content.Category,

    @Json(name = "subscribed")
    var subscribed: Boolean

) : Parcelable