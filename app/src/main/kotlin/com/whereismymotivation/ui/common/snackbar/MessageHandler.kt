package com.whereismymotivation.ui.common.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.whereismymotivation.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun MessageHandler(
    snackbarHostState: SnackbarHostState,
    messenger: Messenger,
) {
    val context = LocalContext.current
    LaunchedEffect("message") {
        messenger.message.onEach {
            val result = snackbarHostState
                .showSnackbar(
                    message = it.content,
                    actionLabel = context.getString(R.string.ok),
                    duration = SnackbarDuration.Short
                )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    /* Handle snackbar action performed */
                }

                SnackbarResult.Dismissed -> {
                    /* Handle snackbar dismissed */
                }
            }
        }.launchIn(this)

        messenger.messageRes.onEach {
            val result = snackbarHostState
                .showSnackbar(
                    message = context.getString(it.content),
                    actionLabel = context.getString(R.string.ok),
                    duration = SnackbarDuration.Short
                )
            when (result) {
                SnackbarResult.ActionPerformed -> {
                    /* Handle snackbar action performed */
                }

                SnackbarResult.Dismissed -> {
                    /* Handle snackbar dismissed */
                }
            }
        }.launchIn(this)

        messenger.clear.onEach {
            snackbarHostState.currentSnackbarData?.dismiss()
        }.launchIn(this)
    }
}