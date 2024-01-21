package com.whereismymotivation.ui.common.browser

import android.net.Uri
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.UniversalSearchResult
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ContentBrowser @Inject constructor(
    private val navigator: Navigator,
    private val chromeTabHelper: ChromeTabHelper
) {
    fun show(content: Content) =
        when (content.category) {
            Content.Category.AUDIO, Content.Category.VIDEO, Content.Category.ARTICLE ->
                chromeTabHelper.openCustomTab(Uri.parse(content.extra))

            Content.Category.IMAGE -> {}
            Content.Category.FACEBOOK_VIDEO -> {}
            Content.Category.QUOTE -> {}
            Content.Category.MENTOR_INFO ->
                navigator.navigateTo(Destination.Mentor.dynamicRoute(content.id))

            Content.Category.TOPIC_INFO ->
                navigator.navigateTo(Destination.Topic.dynamicRoute(content.id))

            Content.Category.YOUTUBE ->
                navigator.navigateTo(Destination.YouTube.dynamicRoute(content.id))

        }

    fun show(result: UniversalSearchResult) {
        when (result.category) {
            Content.Category.AUDIO, Content.Category.VIDEO, Content.Category.ARTICLE ->
                chromeTabHelper.openCustomTab(Uri.parse(result.extra))

            Content.Category.IMAGE -> {}
            Content.Category.FACEBOOK_VIDEO -> {}
            Content.Category.QUOTE -> {}
            Content.Category.YOUTUBE ->
                navigator.navigateTo(Destination.YouTube.dynamicRoute(result.id))

            Content.Category.MENTOR_INFO ->
                navigator.navigateTo(Destination.Mentor.dynamicRoute(result.id))

            Content.Category.TOPIC_INFO -> {
                navigator.navigateTo(Destination.Topic.dynamicRoute(result.id))
            }
        }
    }
}