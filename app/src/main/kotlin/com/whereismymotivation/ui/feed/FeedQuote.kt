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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereismymotivation.R
import com.whereismymotivation.ui.common.text.AutoSizeText
import com.whereismymotivation.ui.theme.AppTheme
import com.whereismymotivation.ui.theme.quoteAmaranth
import com.whereismymotivation.ui.theme.quoteBerkshire
import com.whereismymotivation.ui.theme.quoteOleoScript
import com.whereismymotivation.ui.theme.quoteOswald
import com.whereismymotivation.ui.theme.quotePayToneOne
import com.whereismymotivation.ui.theme.quoteSail
import com.whereismymotivation.ui.theme.white
import com.whereismymotivation.utils.display.FontUtils

@Composable
fun FeedQuote(
    modifier: Modifier = Modifier,
    saying: String,
    author: String
) {
    val font = remember { FontUtils.getFont(saying.length) }
    val random = remember { (0..4).random() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = when (random) {
                    0 -> Brush.linearGradient(
                        colors = listOf(
                            Color(0xff108dc7),
                            Color(0xffef8e38),
                        ),
                    )

                    1 -> Brush.linearGradient(
                        colors = listOf(
                            Color(0xff22c1c3),
                            Color(0xff2948ff),
                        ),
                    )

                    2 -> Brush.linearGradient(
                        colors = listOf(
                            Color(0xffc0392b),
                            Color(0xff8e44ad),
                        ),
                    )

                    3 -> Brush.linearGradient(
                        colors = listOf(
                            Color(0xffA770EF),
                            Color(0xffCF8BF3),
                            Color(0xffFDB99B),
                        ),
                    )

                    else -> Brush.linearGradient(
                        colors = listOf(
                            Color(0xff12c2e9),
                            Color(0xffc471ed),
                            Color(0xfff64f59)
                        ),
                    )
                }
            )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_quotation_mark),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.white,
            modifier = Modifier
                .padding(top = 16.dp)
                .size(32.dp)
                .align(Alignment.CenterHorizontally)
                .alpha(0.7f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(8.dp)
        ) {
            AutoSizeText(
                text = saying,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.white,
                modifier = modifier
                    .align(Alignment.Center)
                    .alpha(0.9f),
                maxLines = 4,
                style = when (font) {
                    FontUtils.FontName.SAIL -> MaterialTheme.typography.quoteSail
                    FontUtils.FontName.AMARANTH -> MaterialTheme.typography.quoteAmaranth
                    FontUtils.FontName.PLAY_TONE_ONE -> MaterialTheme.typography.quotePayToneOne
                    FontUtils.FontName.BERKSHIRE -> MaterialTheme.typography.quoteBerkshire
                    FontUtils.FontName.OLEO_SCRIPT -> MaterialTheme.typography.quoteOleoScript
                    FontUtils.FontName.OSWALD -> MaterialTheme.typography.quoteOswald
                },
                defaultFontSize = 32.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .width(100.dp)
                .height(3.dp)
                .background(MaterialTheme.colorScheme.white)
                .align(Alignment.CenterHorizontally)
                .alpha(0.7f)
        )

        Text(
            text = author,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
                .alpha(0.9f),
            color = MaterialTheme.colorScheme.white
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_quotation_mark),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.white,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .size(32.dp)
                .scale(1f, -1f)
                .align(Alignment.CenterHorizontally)
                .alpha(0.7f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedQuotePreview() {
    AppTheme {
        FeedQuote(
            saying = "Don't let what you cannot do interfere with what you can do.",
            author = "Anonymous"
        )
    }
}