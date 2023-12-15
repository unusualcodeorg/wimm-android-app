package com.whereismymotivation.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.whereismymotivation.ui.home.home
import com.whereismymotivation.ui.splash.Splash
import com.whereismymotivation.ui.splash.SplashViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Destination.Splash.route,
    navigator: Navigator,
    finish: () -> Unit = {},
) {
    NavigationHandler(
        navController = navController,
        navigator = navigator,
        finish = finish
    )

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destination.Splash.route) {
            val viewModel: SplashViewModel = hiltViewModel()
            Splash(
                modifier = modifier,
                viewModel = viewModel,
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

@Composable
private fun NavigationHandler(
    navController: NavController,
    navigator: Navigator,
    finish: () -> Unit
) {
    LaunchedEffect("navigation") {
        navigator.navigate.onEach {
            if (it.popBacktrack) navController.popBackStack()
            navController.navigate(Destination.Home.route)
        }.launchIn(this)

        navigator.back.onEach {
            navController.popBackStack()
        }.launchIn(this)

        navigator.end.onEach {
            finish()
        }.launchIn(this)
    }
}
