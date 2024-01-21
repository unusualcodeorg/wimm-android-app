package com.whereismymotivation.data.remote.apis.content

object Endpoints {

    const val SIMILAR_CONTENTS = "contents/content/{id}/similar"
    const val CONTENTS = "contents/rotated"
    const val MENTOR_CONTENTS = "contents/mentor/{id}"
    const val TOPIC_CONTENTS = "contents/topic/{id}"
    const val META_CONTENT = "meta/content"
    const val MY_BOX_CONTENTS = "contents/my/box"
    const val CREATE_PRIVATE_CONTENT = "content/private"
    const val DELETE_PRIVATE_CONTENT = "content/private/id/{id}"
    const val BOOKMARK_CONTENT = "content/bookmark"
    const val REMOVE_CONTENT_BOOKMARK = "content/bookmark/{contentId}"
    const val SUBMIT_PRIVATE_CONTENT = "content/private/submit"
    const val UNSUBMIT_PRIVATE_CONTENT = "content/private/unsubmit"
    const val PUBLISH_GENERAL_CONTENT = "content/admin/publish/general"
    const val UNPUBLISH_GENERAL_CONTENT = "content/admin/unpublish/general"

    const val CONTENT_DETAIL = "content/id/{id}"
    const val CONTENT_MARK_VIEW = "content/mark/view"
    const val CONTENT_MARK_LIKE = "content/mark/like"
    const val CONTENT_MARK_UNLIKE = "content/mark/unlike"
    const val CONTENT_MARK_SHARE = "content/mark/share"

    const val SEARCH = "search"

}