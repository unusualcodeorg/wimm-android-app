package com.whereismymotivation.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Destination private constructor(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {

    data object Splash : Destination("splash")
    data object Login : Destination("login")

    data object Home : Destination("home") {
        data object Feed : Destination("home/feed")
        data object Mentors : Destination("home/mentors")
        data object MyBox : Destination("home/my_box")
        data object Search : Destination("home/search")
        data object Profile : Destination("home/profile")
    }

    data object Mentor : Destination(
        route = "mentor/{mentorId}",
        navArguments = listOf(navArgument("mentorId") {
            type = NavType.StringType
        })
    ) {
        fun createRoute(mentorId: String) = "mentor/${mentorId}"
    }

    data object YouTube : Destination(
        route = "youtube/{contentId}",
        navArguments = listOf(navArgument("contentId") {
            type = NavType.StringType
        })
    ) {
        fun createRoute(contentId: String) = "youtube/${contentId}"
    }

}