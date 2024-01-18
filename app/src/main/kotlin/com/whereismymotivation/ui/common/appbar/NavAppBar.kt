package com.whereismymotivation.ui.common.appbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whereismymotivation.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    upPress: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {

    Column {


        TopAppBar(
            modifier = modifier,
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            navigationIcon = {
                IconButton(onClick = upPress) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            actions = actions,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
        )
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
private fun NavAppBarPreview() {
    AppTheme {
        NavAppBar(
            modifier = Modifier,
            title = "Mentor",
            upPress = {},
            actions = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = null
                    )
                }
            }
        )
    }
}