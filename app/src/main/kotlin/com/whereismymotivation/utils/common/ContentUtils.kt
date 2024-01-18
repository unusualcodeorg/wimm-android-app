package com.whereismymotivation.utils.common

import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.User

object ContentUtils {

    fun getDeeplinkDataMap(content: Content): HashMap<String, String> {
        val dataMap = HashMap<String, String>()
        content.run {
            dataMap[Constants.DEEPLINK_KEY_ID] = id
            dataMap[Constants.DEEPLINK_KEY_TYPE] = category.name
            dataMap[Constants.DEEPLINK_KEY_PRIMARY] = title
            dataMap[Constants.DEEPLINK_KEY_SECONDARY] = subtitle
            dataMap[Constants.DEEPLINK_KEY_EXTRA] = extra
            dataMap[Constants.DEEPLINK_KEY_TERTIARY] = thumbnail
        }
        return dataMap
    }

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