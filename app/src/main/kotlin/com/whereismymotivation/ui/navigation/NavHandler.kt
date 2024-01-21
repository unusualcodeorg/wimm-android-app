package com.whereismymotivation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun NavHandler(
    navController: NavController,
    navigator: Navigator,
    finish: () -> Unit
) {
    LaunchedEffect("navigation") {
        navigator.navigate.onEach {
            if (it.popBackstack) navController.popBackStack()
            navController.navigate(it.route)
        }.launchIn(this)

        navigator.deeplink.onEach {
            if (navController.graph.hasDeepLink(it.uri)) {
                val reached = navController.currentDestination?.hasDeepLink(it.uri) ?: false
                if (!reached) {
                    if (it.popBackstack) navController.popBackStack()
                    navController.navigate(it.uri)
                }
            }
        }.launchIn(this)

        navigator.back.onEach {
            if (it.recreate) {
                navController.previousBackStackEntry?.destination?.route?.let { route ->
                    navController.navigate(route) {
                        popUpTo(route) { inclusive = true }
                    }
                }
            } else {
                navController.navigateUp()
            }
        }.launchIn(this)

        navigator.end.onEach {
            finish()
        }.launchIn(this)
    }
}