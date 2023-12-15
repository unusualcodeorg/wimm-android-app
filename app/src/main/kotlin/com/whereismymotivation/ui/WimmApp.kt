package com.whereismymotivation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.whereismymotivation.ui.home.HomeBottomBar
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun WimmApp(finishActivity: () -> Unit) {
    AppTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { HomeBottomBar(navController = navController) },
            content = { innerPaddingModifier ->
                NavGraph(
                    finishActivity = finishActivity,
                    navController = navController,
                    modifier = Modifier.padding(innerPaddingModifier)
                )
            }
        )
    }
}