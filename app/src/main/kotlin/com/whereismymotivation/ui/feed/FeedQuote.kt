package com.whereismymotivation.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereismymotivation.R
import com.whereismymotivation.ui.theme.AppTheme
import com.whereismymotivation.ui.theme.quote

@Composable
fun FeedQuote(modifier: Modifier = Modifier, saying: String, author: String) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_quotation_mark),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .size(32.dp)
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(8.dp)
        ) {
            Text(
                text = saying,
                style = MaterialTheme.typography.quote,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                modifier = modifier
                    .align(Alignment.Center)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                fontSize = 28.sp,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .width(100.dp)
                .height(3.dp)
                .background(MaterialTheme.colorScheme.primary)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = author,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.primary
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_quotation_mark),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(32.dp)
                .scale(1f, -1f)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp)
        )
    }
}

@Preview
@Composable
private fun FeedQuotePreview() {
    AppTheme {
        FeedQuote(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            saying = "Don't let what you cannot do interfere with what you can do.",
            author = "Anonymous"
        )
    }
}