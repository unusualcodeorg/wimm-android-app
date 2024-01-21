package com.whereismymotivation.ui.profile

import androidx.annotation.Keep

@Keep
enum class ProfileTab(val title: String, val index: Int) {
    MOOD("Mood", 0),
    JOURNAL("Journal", 1);

    companion object {
        fun fromName(name: String): ProfileTab =
            try {
                valueOf(name)
            } catch (e: IllegalArgumentException) {
                MOOD
            }
    }
}