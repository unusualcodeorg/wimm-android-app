package com.whereismymotivation.fcm.core

interface Notification {
    suspend fun send()

    enum class Type(val value: Int) {
        UNKNOWN(0),
        TEXT(1),
        IMAGE(2),
        TEXT_AND_IMAGE(3),
        CONTENT(4),
        MOOD(5);

        companion object {
            fun parse(name: String) =
                when (name) {
                    "TEXT" -> TEXT
                    "IMAGE" -> IMAGE
                    "TEXT_AND_IMAGE" -> TEXT_AND_IMAGE
                    "CONTENT" -> CONTENT
                    "MOOD" -> MOOD
                    else -> UNKNOWN
                }
        }
    }

    enum class Action(val requestCode: Int) {
        APP_OPEN(100),
        CONTENT_VIEW(200),
        MOOD_RECORD(300),
        JOURNAL_RECORD(400);
    }
}