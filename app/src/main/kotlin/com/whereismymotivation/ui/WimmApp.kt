package com.whereismymotivation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.progress.Loading
import com.whereismymotivation.ui.common.share.ContentShareHandler
import com.whereismymotivation.ui.common.share.Sharer
import com.whereismymotivation.ui.common.snackbar.AppSnackbar
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.home.HomeBottomBar
import com.whereismymotivation.ui.navigation.NavGraph
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun WimmApp(
    navigator: Navigator,
    loader: Loader,
    sharer: Sharer<Content>,
    messenger: Messenger,
    finish: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    AppTheme {
        val navController = rememberNavController()
        Scaffold(
            modifier = Modifier.imePadding(),
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
            Loading(
                modifier = Modifier
                    .padding(innerPaddingModifier)
                    .fillMaxWidth(),
                loader = loader
            )
            ContentShareHandler(sharer)
        }
    }
}