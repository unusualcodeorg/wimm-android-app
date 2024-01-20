package com.whereismymotivation.fcm.core

interface Notification {
    suspend fun send()

    enum class Type(val value: Int) {
        UNKNOWN(0),
        TEXT(1000),
        IMAGE(2000),
        TEXT_AND_IMAGE(3000),
        CONTENT(4000),
        MOOD(5000);

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
        APP_OPEN(1),
        CONTENT_VIEW(2),
        MOOD_RECORD(3),
        JOURNAL_RECORD(4);
    }
}