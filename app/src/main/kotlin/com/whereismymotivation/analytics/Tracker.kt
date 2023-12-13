package com.whereismymotivation.analytics

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Tracker @Inject constructor(private val client: TrackingClient) {

    fun trackAppOpen() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.APP_OPEN))

    fun trackSplashScreenOpen() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.SPLASH_SCREEN_STATE),
            AnalyticsEventParam(AnalyticsEventParam.Name.STATE, AnalyticsEventParam.Value.OPEN)
        )

    fun trackHomeScreenOpen() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.HOME_SCREEN_STATE),
            AnalyticsEventParam(AnalyticsEventParam.Name.STATE, AnalyticsEventParam.Value.OPEN)
        )

    fun trackMentorDetailScreenOpen() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.MENTOR_DETAILS_SCREEN_STATE),
            AnalyticsEventParam(AnalyticsEventParam.Name.STATE, AnalyticsEventParam.Value.OPEN)
        )

    fun trackTopicDetailScreenOpen() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.TOPIC_DETAILS_SCREEN_STATE),
            AnalyticsEventParam(AnalyticsEventParam.Name.STATE, AnalyticsEventParam.Value.OPEN)
        )

    fun trackMentorsScreenOpen() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.MENTORS_SCREEN_STATE),
            AnalyticsEventParam(AnalyticsEventParam.Name.STATE, AnalyticsEventParam.Value.OPEN)
        )

    fun trackTopicsScreenOpen() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.TOPICS_SCREEN_STATE),
            AnalyticsEventParam(AnalyticsEventParam.Name.STATE, AnalyticsEventParam.Value.OPEN)
        )

    fun trackSearchScreenOpen() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.SEARCH_SCREEN_STATE),
            AnalyticsEventParam(AnalyticsEventParam.Name.STATE, AnalyticsEventParam.Value.OPEN)
        )

    fun trackWelcomeScreenOpen() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.WELCOME_SCREEN_STATE),
            AnalyticsEventParam(AnalyticsEventParam.Name.STATE, AnalyticsEventParam.Value.OPEN)
        )

    fun trackYoutubeScreenOpen() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.YOUTUBE_SCREEN_STATE),
            AnalyticsEventParam(AnalyticsEventParam.Name.STATE, AnalyticsEventParam.Value.OPEN)
        )

    fun trackYoutubeWebScreenOpen() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.YOUTUBE_WEB_SCREEN_STATE),
            AnalyticsEventParam(AnalyticsEventParam.Name.STATE, AnalyticsEventParam.Value.OPEN)
        )

    fun trackFacebookVideoScreenOpen() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.FACEBOOK_VIDEO_SCREEN_STATE),
            AnalyticsEventParam(AnalyticsEventParam.Name.STATE, AnalyticsEventParam.Value.OPEN)
        )

    fun trackImageScreenOpen() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.IMAGE_SCREEN_STATE),
            AnalyticsEventParam(AnalyticsEventParam.Name.STATE, AnalyticsEventParam.Value.OPEN)
        )

    fun trackQuoteScreenOpen() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.QUOTE_SCREEN_STATE),
            AnalyticsEventParam(AnalyticsEventParam.Name.STATE, AnalyticsEventParam.Value.OPEN)
        )

    fun trackShareContent(id: String, contentType: String, medium: String) =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.SHARE_CONTENT),
            AnalyticsEventParam(AnalyticsEventParam.Name.CATEGORY, contentType),
            AnalyticsEventParam(AnalyticsEventParam.Name.ITEM, id),
            AnalyticsEventParam(AnalyticsEventParam.Name.MEDIUM, medium)
        )

    fun trackLikeContent(id: String, contentType: String) =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.LIKE_CONTENT),
            AnalyticsEventParam(AnalyticsEventParam.Name.CATEGORY, contentType),
            AnalyticsEventParam(AnalyticsEventParam.Name.ITEM, id)
        )

    fun trackBookmarkContent(id: String, contentType: String) =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.BOOKMARK_CONTENT),
            AnalyticsEventParam(AnalyticsEventParam.Name.CATEGORY, contentType),
            AnalyticsEventParam(AnalyticsEventParam.Name.ITEM, id)
        )

    fun trackRatingShown() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.DIALOG_RATE_US_SHOWN))

    fun trackRatingGiven(rating: Long?) =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.DIALOG_RATE_US_STARS),
            AnalyticsEventParam(AnalyticsEventParam.Name.QUANTITY, rating)
        )

    fun trackRateUsSubmit() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.DIALOG_RATE_US_CHOICE),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.SUBMIT)
        )

    fun trackRateUsCancel() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.DIALOG_RATE_US_CHOICE),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.CANCEL)
        )

    fun trackRateUsLater() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.DIALOG_RATE_US_CHOICE),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.LATER)
        )

    fun trackRatingWithMessageGiven(rating: Long?, message: String) =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.DIALOG_RATE_US_STARS),
            AnalyticsEventParam(AnalyticsEventParam.Name.VALUE, rating),
            AnalyticsEventParam(AnalyticsEventParam.Name.CONTENT, message)
        )

    fun trackGoToPlayStore() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.DIALOG_RATE_US_CHOICE),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.PLAY_STORE)
        )

    fun trackFeedbackSubmit() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.DIALOG_FEEDBACK_CHOICE),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.SUBMIT)
        )

    fun trackFeedbackCancel() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.DIALOG_FEEDBACK_CHOICE),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.CANCEL)
        )

    fun trackFacebookLogin() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.LOGIN),
            AnalyticsEventParam(AnalyticsEventParam.Name.LOGIN_MODE, AnalyticsEventParam.Value.FACEBOOK)
        )

    fun trackGoogleLogin() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.LOGIN),
            AnalyticsEventParam(AnalyticsEventParam.Name.LOGIN_MODE, AnalyticsEventParam.Value.GOOGLE)
        )

    fun trackHomeSelected() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.SELECTED_APP_SECTION),
            AnalyticsEventParam(AnalyticsEventParam.Name.SECTION, AnalyticsEventParam.Value.HOME)
        )

    fun trackMentorsSelected() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.SELECTED_APP_SECTION),
            AnalyticsEventParam(AnalyticsEventParam.Name.SECTION, AnalyticsEventParam.Value.MENTORS)
        )

    fun trackMotivationBoxSelected() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.SELECTED_APP_SECTION),
            AnalyticsEventParam(AnalyticsEventParam.Name.SECTION, AnalyticsEventParam.Value.MOTIVATION_BOX)
        )

    fun trackSearchSelected() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.SELECTED_APP_SECTION),
            AnalyticsEventParam(AnalyticsEventParam.Name.SECTION, AnalyticsEventParam.Value.SEARCH)
        )

    fun trackProfileSelected() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.SELECTED_APP_SECTION),
            AnalyticsEventParam(AnalyticsEventParam.Name.SECTION, AnalyticsEventParam.Value.PROFILE)
        )

    fun trackUpdateAppSettingSelected() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.SETTINGS_UPDATE_APP))

    fun trackFeedbackSettingSelected() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.SETTINGS_FEEDBACK))

    fun trackRateUsSettingSelected() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.SETTINGS_RATE_US))

    fun trackInviteSettingSelected() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.SETTINGS_INVITE_YOUR_FRIEND))

    fun trackLogoutSettingSelected() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.SETTINGS_LOGOUT))

    fun trackLoginSettingsSelected() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.SETTINGS_LOGIN))

    fun trackHomeSettingsSelected() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.HOME_SCREEN_SETTINGS))

    fun trackProfileLoginSelected() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.PROFILE_SCREEN_LOGIN))

    fun trackMentorsScreenSearchClick() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.MENTORS_SEARCH_MORE_CLICK))

    fun trackSearchScreenTopicSearchClick() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.SEARCH_MORE_TOPICS_CLICK),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.SEARCH_TOPIC)
        )

    fun trackSearchScreenSearchClick() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.SEARCH_BAR_CLICK),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.SEARCH)
        )

    fun trackMentorDetailScreenSubscribeClick() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.MENTOR_FOLLOW_CHANGE_CLICK),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.SUBSCRIBE_MENTOR)
        )

    fun trackMentorDetailScreenRemoveClick() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.MENTOR_FOLLOW_CHANGE_CLICK),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.REMOVE_MENTOR)
        )

    fun trackTopicDetailScreenSubscribeClick() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.TOPIC_FOLLOW_CHANGE_CLICK),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.SUBSCRIBE_TOPIC)
        )

    fun trackTopicDetailScreenRemoveClick() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.TOPIC_FOLLOW_CHANGE_CLICK),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.REMOVE_TOPIC)
        )

    fun trackWelcomeScreenDoneClick(selectionCount: Int) =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.WELCOME_DONE_CLICK),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.REMOVE_TOPIC),
            AnalyticsEventParam(AnalyticsEventParam.Name.QUANTITY, selectionCount)
        )

    fun trackTopicDetailScreenContentShow() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.TOPIC_CONTENTS_TOGGLE),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.CONTENT_SHOW)
        )

    fun trackTopicDetailScreenContentHide() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.TOPIC_CONTENTS_TOGGLE),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.CONTENT_HIDE)
        )

    fun trackMentorDetailScreenContentShow() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.MENTOR_CONTENTS_TOGGLE),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.CONTENT_SHOW)
        )

    fun trackMentorDetailScreenContentHide() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.MENTOR_CONTENTS_TOGGLE),
            AnalyticsEventParam(AnalyticsEventParam.Name.CHOICE, AnalyticsEventParam.Value.CONTENT_HIDE)
        )

    fun trackServerNotificationReceived() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.SERVER_NOTIFICATION_RECEIVED))

    fun trackServerNotificationClick() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.SERVER_NOTIFICATION_CLICK))

    fun trackServerNotificationShown() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.SERVER_NOTIFICATION_SHOWN))

    fun trackServerNotificationShareOthers() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.SERVER_NOTIFICATION_SHARE_OTHERS))

    fun trackServerNotificationShareWhatsApp() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.SERVER_NOTIFICATION_SHARE_WHATSAPP))

    fun trackHomeFeedContentClick(id: String, contentType: String) =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.HOME_FEED_CONTENT_CLICK),
            AnalyticsEventParam(AnalyticsEventParam.Name.CATEGORY, contentType),
            AnalyticsEventParam(AnalyticsEventParam.Name.ITEM, id)
        )

    fun trackSheetContentClick(id: String, contentType: String) =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.SHEET_CONTENT_CLICK),
            AnalyticsEventParam(AnalyticsEventParam.Name.CATEGORY, contentType),
            AnalyticsEventParam(AnalyticsEventParam.Name.ITEM, id)
        )

    fun trackSearchResultClick(id: String, contentType: String) =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.SEARCH_RESULT_CLICK),
            AnalyticsEventParam(AnalyticsEventParam.Name.CATEGORY, contentType),
            AnalyticsEventParam(AnalyticsEventParam.Name.ITEM, id)
        )

    fun trackMentorCardClick(id: String, name: String) =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.MENTOR_CARD_CLICK),
            AnalyticsEventParam(AnalyticsEventParam.Name.VALUE, name),
            AnalyticsEventParam(AnalyticsEventParam.Name.ITEM, id)
        )

    fun trackTopicCardClick(id: String, name: String) =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.TOPIC_CARD_CLICK),
            AnalyticsEventParam(AnalyticsEventParam.Name.VALUE, name),
            AnalyticsEventParam(AnalyticsEventParam.Name.ITEM, id)
        )

    fun trackMoodNotificationReceived() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.MOOD_NOTIFICATION_RECEIVED))

    fun trackMoodNotificationClick() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.MOOD_NOTIFICATION_CLICK))

    fun trackMoodNotificationShown() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.MOOD_NOTIFICATION_SHOWN))

    fun trackMoodNotificationMoodRecord() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.MOOD_NOTIFICATION_MOOD_RECORD))

    fun trackMoodNotificationJournalRecord() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.MOOD_NOTIFICATION_JOURNAL_RECORD))

    fun trackProfileMoodSelected() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.MOOD_SELECTED),
            AnalyticsEventParam(AnalyticsEventParam.Name.MEDIUM, AnalyticsEventParam.Value.PROFILE)
        )

    fun trackNotificationMoodSelected() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.MOOD_SELECTED),
            AnalyticsEventParam(AnalyticsEventParam.Name.MEDIUM, AnalyticsEventParam.Value.NOTIFICATION)
        )

    fun trackProfileJournalAdded() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.JOURNAL_ADDED),
            AnalyticsEventParam(AnalyticsEventParam.Name.MEDIUM, AnalyticsEventParam.Value.PROFILE)
        )

    fun trackProfileJournalRemoved() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.JOURNAL_REMOVED),
            AnalyticsEventParam(AnalyticsEventParam.Name.MEDIUM, AnalyticsEventParam.Value.PROFILE)
        )

    fun trackNotificationJournalAdded() =
        client.track(
            AnalyticsEvent(AnalyticsEvent.Name.JOURNAL_ADDED),
            AnalyticsEventParam(AnalyticsEventParam.Name.MEDIUM, AnalyticsEventParam.Value.NOTIFICATION)
        )

    fun trackMoodNotificationScreenShow() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.MOOD_NOTIFICATION_SCREEN_SHOW))

    fun trackMoodNotificationScreenDismiss() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.MOOD_NOTIFICATION_SCREEN_DISMISS))

    fun trackMoodNotificationButtonDismissClick() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.MOOD_NOTIFICATION_BUTTON_DISMISS_CLICK))

    fun trackMoodNotificationEmptySpaceClick() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.MOOD_NOTIFICATION_EMPTY_SPACE_CLICK))

    fun trackMoodAndJournalSyncSuccess() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.MOOD_AND_JOURNAL_SYNC_SUCCESS))

    fun trackMoodAndJournalSyncFailure() =
        client.track(AnalyticsEvent(AnalyticsEvent.Name.MOOD_AND_JOURNAL_SYNC_FAILURE))
}