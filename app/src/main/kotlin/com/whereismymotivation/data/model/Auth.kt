package com.whereismymotivation.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Auth(

    @Json(name = "user")
    val user: User,

    @Json(name = "tokens")
    val token: Token

) : Parcelable