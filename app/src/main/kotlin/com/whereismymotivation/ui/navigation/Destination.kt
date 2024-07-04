package com.whereismymotivation.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

object Destination {
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Onboarding : Screen("onboarding")
    data object ServerUnreachable : Screen("server-unreachable")
    data object ExploreMentors : Screen("explore-mentor")
    data object Mentor : DynamicScreen("mentor", "mentorId")
    data object Topic : DynamicScreen("topic", "topicId")
    data object YouTube : DynamicScreen("youtube", "contentId")
    data object Content : DynamicScreen("content", "contentId")

    data object Home : Screen("home") {
        data object Feed : Screen("home/feed")
        data object Mentors : Screen("home/mentors")
        data object MyBox : Screen("home/my_box")
        data object Search : DynamicScreen("home/search", "searchMode")
        data object Profile : DynamicScreen("home/profile", "profileTab")
    }

    abstract class Screen(baseRoute: String) {
        companion object {
            const val BASE_DEEPLINK_URL = "app://wimm"
        }

        open val route = baseRoute
        open val deeplink = "${BASE_DEEPLINK_URL}/$baseRoute"
    }

    abstract class DynamicScreen(
        private val baseRoute: String,
        val routeArgName: String,
    ) : Screen(baseRoute) {

        val navArguments = listOf(navArgument(routeArgName) { type = NavType.StringType })

        override val route = "$baseRoute/{$routeArgName}"
        override val deeplink = "${BASE_DEEPLINK_URL}/$baseRoute/{$routeArgName}"

        fun dynamicRoute(param: String) = "$baseRoute/$param"

        fun dynamicDeeplink(param: String) = "$BASE_DEEPLINK_URL/$baseRoute/${param}"
    }
}

