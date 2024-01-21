package com.whereismymotivation.fcm.core

data class Payload(
    val type: Notification.Type,
    val ticker: String,
    val title: String,
    val subtitle: String,
    val message: String? = null,
    val thumbnail: String? = null,
    val extra: String? = null,
) {
    fun toMap(): Map<String, String?> {
        val map = mutableMapOf<String, String>()
        map["type"] = type.name
        map["ticker"] = ticker
        map["title"] = title
        map["subtitle"] = subtitle
        message?.let { map["message"] = it }
        thumbnail?.let { map["thumbnail"] = it }
        extra?.let { map["extra"] = it }
        return map
    }
}

fun Map<String, Any>.toStringMap(): Map<String, String> {
    val convertedMap = mutableMapOf<String, String>()
    for ((key, value) in this) {
        if (value is String) {
            convertedMap[key] = value
        } else {
            convertedMap[key] = value.toString()
        }
    }
    return convertedMap
}

fun Map<String, String>.toPayload(): Payload {
    return Payload(
        type = Notification.Type.parse(this["type"] ?: ""),
        ticker = this["ticker"] ?: "",
        title = this["title"] ?: "",
        subtitle = this["subtitle"] ?: "",
        message = this["message"],
        thumbnail = this["thumbnail"],
        extra = this["extra"],
    )
}