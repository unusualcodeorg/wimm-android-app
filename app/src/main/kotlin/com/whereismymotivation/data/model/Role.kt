package com.whereismymotivation.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Role(

    @Json(name = "_id")
    val id: String,

    @Json(name = "code")
    val code: RoleCode

) : Parcelable {
    @Keep
    enum class RoleCode(val value: String) {
        VIEWER("VIEWER"),
        ADMIN("ADMIN"),
        MANAGER("MANAGER"),
    }
}