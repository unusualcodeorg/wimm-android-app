package com.whereismymotivation.utils.common

import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.User

object ContentUtils {

    private fun parse(type: String): Content.Category =
        when (type) {
            Content.Category.AUDIO.type -> Content.Category.AUDIO
            Content.Category.VIDEO.type -> Content.Category.VIDEO
            Content.Category.IMAGE.type -> Content.Category.IMAGE
            Content.Category.YOUTUBE.type -> Content.Category.YOUTUBE
            Content.Category.FACEBOOK_VIDEO.type -> Content.Category.FACEBOOK_VIDEO
            Content.Category.ARTICLE.type -> Content.Category.ARTICLE
            Content.Category.QUOTE.type -> Content.Category.QUOTE
            Content.Category.MENTOR_INFO.type -> Content.Category.MENTOR_INFO
            Content.Category.TOPIC_INFO.type -> Content.Category.TOPIC_INFO
            else -> Content.Category.ARTICLE
        }

    fun convertToContent(data: Map<String, String>): Content? {

        val id = data["id"] ?: return null
        val type = data["contentType"] ?: return null
        val title = data["title"] ?: return null
        val subtitle = data["subtitle"] ?: return null
        val thumbnail = data["thumbnail"] ?: return null
        val extra = data["extra"] ?: return null
        data["date"] ?: return null

        return Content(
            id,
            parse(type),
            title,
            subtitle,
            thumbnail,
            extra,
            User(String.Null(), null, String.Null(), null)
        )
    }
}