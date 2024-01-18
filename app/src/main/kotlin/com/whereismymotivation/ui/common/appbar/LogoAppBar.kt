package com.whereismymotivation.ui.common.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whereismymotivation.R
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun LogoAppBar(
    modifier: Modifier = Modifier,
    title: String,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Column {
        Surface(
            modifier = modifier.height(80.dp),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .height(56.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(id = R.drawable.wimm_logo),
                    contentDescription = null
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.weight(1f))
                actions()
            }
        }
        Divider()
    }
}

@Preview
@Composable
private fun LogoAppBarPreview() {
    AppTheme {
        LogoAppBar(
            title = stringResource(R.string.app_name),
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