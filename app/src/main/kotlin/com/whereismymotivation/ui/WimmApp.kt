package com.whereismymotivation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.whereismymotivation.ui.home.HomeBottomBar
import com.whereismymotivation.ui.navigation.NavGraph
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun WimmApp(navigator: Navigator, finish: () -> Unit) {
    AppTheme {
        val navController = rememberNavController()
        Scaffold(
            // it will render bottom bar only in the home route
            bottomBar = { HomeBottomBar(navController = navController) },
            content = { innerPaddingModifier ->
                NavGraph(
                    navController = navController,
                    modifier = Modifier.padding(innerPaddingModifier),
                    navigator = navigator,
                    finish = finish
                )
            }
        )
    }
}