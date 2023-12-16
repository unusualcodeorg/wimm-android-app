package com.whereismymotivation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.whereismymotivation.ui.home.HomeBottomBar
import com.whereismymotivation.ui.message.AppSnackbar
import com.whereismymotivation.ui.message.Messenger
import com.whereismymotivation.ui.navigation.NavGraph
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun WimmApp(navigator: Navigator, messenger: Messenger, finish: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    AppTheme {
        val navController = rememberNavController()
        Scaffold(
            snackbarHost = { AppSnackbar(snackbarHostState, messenger) },
            // it will render bottom bar only in the home route
            bottomBar = { HomeBottomBar(navController = navController) },
        ) { innerPaddingModifier ->
            NavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPaddingModifier),
                navigator = navigator,
                finish = finish
            )
        }
    }
}