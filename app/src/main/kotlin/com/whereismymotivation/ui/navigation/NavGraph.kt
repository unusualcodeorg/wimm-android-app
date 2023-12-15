package com.whereismymotivation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.whereismymotivation.ui.home.home
import com.whereismymotivation.ui.login.Login
import com.whereismymotivation.ui.login.LoginViewModel
import com.whereismymotivation.ui.splash.Splash
import com.whereismymotivation.ui.splash.SplashViewModel

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Destination.Splash.route,
    navigator: Navigator,
    finish: () -> Unit = {},
) {
    NavHandler(
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
        composable(Destination.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            Login(
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
