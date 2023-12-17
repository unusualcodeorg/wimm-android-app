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

        navigator.back.onEach {
            navController.navigateUp()
        }.launchIn(this)

        navigator.end.onEach {
            finish()
        }.launchIn(this)
    }
}