package com.whereismymotivation.ui

import androidx.navigation.NamedNavArgument

sealed class Destination(
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
}