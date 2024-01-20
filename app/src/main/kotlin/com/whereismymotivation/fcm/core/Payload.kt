package com.whereismymotivation.fcm.core

data class Payload(
    val type: Notification.Type,
    val ticker: String,
    val title: String,
    val subtitle: String,
    val message: String? = null,
    val thumbnail: String? = null
)

fun Map<String, String>.toPayload(): Payload {
    return Payload(
        type = Notification.Type.parse(this["type"] ?: ""),
        ticker = this["ticker"] ?: "",
        title = this["title"] ?: "",
        subtitle = this["subtitle"] ?: "",
        message = this["message"],
        thumbnail = this["thumbnail"],
    )
}