package com.whereismymotivation.data.remote.apis.subscription.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SubscriptionModifyRequest(

    @Json(name = "mentorIds")
    val mentorIds: Array<String>,

    @Json(name = "topicIds")
    val topicIds: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SubscriptionModifyRequest) return false

        if (!mentorIds.contentEquals(other.mentorIds)) return false
        return topicIds.contentEquals(other.topicIds)
    }

    override fun hashCode(): Int {
        var result = mentorIds.contentHashCode()
        result = 31 * result + topicIds.contentHashCode()
        return result
    }
}