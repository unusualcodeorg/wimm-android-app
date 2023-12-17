package com.whereismymotivation.ui.common.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun LogoAppBar(modifier: Modifier = Modifier, title: String) {
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
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
private fun LogoAppBarPreview() {
    LogoAppBar(title = stringResource(R.string.app_name))
}