package com.whereismymotivation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.whereismymotivation.ui.home.home
import com.whereismymotivation.ui.splash.Splash

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destination.Splash.route,
) {

    val splashComplete = remember { mutableStateOf(false) }

    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destination.Splash.route) {
            // Intercept back in Onboarding: make it finish the activity
            BackHandler {
                finishActivity()
            }

            Splash(
                splashComplete = {
                    splashComplete.value = true
                    actions.splashComplete()
                }
            )
        }
        navigation(
            route = Destination.Home.route,
            startDestination = Destination.Home.Feed.route
        ) {
            home(
                modifier = modifier
            )
        }
    }
}

class MainActions(navController: NavHostController) {
    val splashComplete: () -> Unit = {
        navController.popBackStack()
        navController.navigate(Destination.Home.route)
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
