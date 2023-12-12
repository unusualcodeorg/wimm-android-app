package com.whereismymotivation.utils.common

object UrlUtils {

    fun parseYoutubeUrlForId(url: String): String? {
        val pattern =
            "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|be\\.com\\/(?:watch\\?(?:feature=youtu.be\\&)?v=|v\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)"
        val regex = Regex(pattern)
        return regex.find(url)?.run {
            for (match in groups)
                if (match?.value?.contains("http") == false) return@run match.value
            return@run null
        }
    }

    fun parseFacebookVideoUrlForId(url: String): String? {
        val matches = url.split("/")
        if (matches.size > 3) {
            return if (matches[matches.size - 1].isNotBlank()) matches[matches.size - 1]
            else matches[matches.size - 2]
        }
        return url
    }

}