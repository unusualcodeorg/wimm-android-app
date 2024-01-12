package com.whereismymotivation.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class User(

    @Json(name = "_id")
    val id: String,

    @Json(name = "name")
    val name: String?,

    @Json(name = "email")
    val email: String?,

    @Json(name = "profilePicUrl")
    val profilePicUrl: String?,

    @Json(name = "roles")
    val roles: List<Role> = listOf()
) : Parcelable