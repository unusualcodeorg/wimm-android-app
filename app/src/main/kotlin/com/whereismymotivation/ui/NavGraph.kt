package com.whereismymotivation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.whereismymotivation.ui.home.HomeRoute
import com.whereismymotivation.ui.home.home

object RootRoute {
    const val LOGIN = "login"
    const val HOME = "home"
    const val MENTOR_DETAIL = "mentor_details"
    const val TOPIC_DETAIL = "topic_details"
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = RootRoute.HOME,
) {

    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = RootRoute.HOME,
            startDestination = HomeRoute.FEED
        ) {
            home(
                modifier = modifier
            )
        }
    }
}

class MainActions(navController: NavHostController) {

}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
