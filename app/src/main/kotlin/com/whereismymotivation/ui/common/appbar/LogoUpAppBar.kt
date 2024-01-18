package com.whereismymotivation.ui.common.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whereismymotivation.R
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun LogoUpAppBar(
    modifier: Modifier = Modifier,
    color: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    upPress: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = color,
        contentColor = contentColor
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .height(56.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = upPress) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.wimm_logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .size(32.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BackAppBarPreview() {
    AppTheme {
        LogoUpAppBar(modifier = Modifier, upPress = {})
    }
}