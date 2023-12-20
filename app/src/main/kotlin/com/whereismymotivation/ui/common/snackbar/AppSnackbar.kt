package com.whereismymotivation.ui.common.snackbar

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereismymotivation.ui.common.snackbar.Message.Type.ERROR
import com.whereismymotivation.ui.common.snackbar.Message.Type.INFO
import com.whereismymotivation.ui.common.snackbar.Message.Type.SUCCESS
import com.whereismymotivation.ui.common.snackbar.Message.Type.WARNING
import com.whereismymotivation.ui.theme.black
import com.whereismymotivation.ui.theme.info
import com.whereismymotivation.ui.theme.success
import com.whereismymotivation.ui.theme.warning

@Composable
fun AppSnackbar(
    snackbarHostState: SnackbarHostState,
    messenger: Messenger
) {
    MessageHandler(snackbarHostState, messenger)

    val messageType = messenger.messageType.collectAsStateWithLifecycle()

    val color = when (messageType.value) {
        SUCCESS -> MaterialTheme.colorScheme.success
        ERROR -> MaterialTheme.colorScheme.error
        WARNING -> MaterialTheme.colorScheme.warning
        INFO -> MaterialTheme.colorScheme.info
    }

    SnackbarHost(hostState = snackbarHostState) {
        Snackbar(
            snackbarData = it,
            containerColor = color,
            contentColor = MaterialTheme.colorScheme.black,
            actionColor = MaterialTheme.colorScheme.black
        )
    }
}