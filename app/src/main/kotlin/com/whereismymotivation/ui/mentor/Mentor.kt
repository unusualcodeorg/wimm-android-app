package com.whereismymotivation.ui.mentor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.ui.common.appbar.BackAppBar
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.image.OutlinedAvatar
import com.whereismymotivation.ui.common.preview.MentorPreviewParameterProvider
import com.whereismymotivation.ui.common.utils.scrim
import com.whereismymotivation.ui.theme.AppTheme
import com.whereismymotivation.ui.theme.white
import java.util.Locale

@Composable
fun Mentor(modifier: Modifier, viewModel: MentorViewModel) {
    val mentor = viewModel.mentor.collectAsState().value
    MentorView(
        mentor = mentor,
        modifier = modifier
    ) { viewModel.upPress() }
}

@Composable
private fun MentorView(
    modifier: Modifier,
    mentor: Mentor?,
    upPress: () -> Unit
) {
    if (mentor == null) return

    MentorHeader(
        mentor = mentor,
        upPress = upPress
    )
}

@Composable
private fun MentorHeader(
    mentor: Mentor,
    upPress: () -> Unit
) {
    Box {
        NetworkImage(
            url = mentor.thumbnail,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .scrim(colors = listOf(Color(0x80000000), Color(0x33000000)))
                .aspectRatio(4f / 3f)
        )
        BackAppBar(
            contentColor = MaterialTheme.colorScheme.white,
            upPress = upPress
        )
        OutlinedAvatar(
            url = mentor.thumbnail,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.BottomCenter)
                .offset(y = 20.dp) // overlap bottom of image
        )
    }
}

@Composable
private fun MentorBody(modifier: Modifier = Modifier, mentor: Mentor) {
    Column(modifier = modifier) {
        Text(
            text = mentor.occupation.uppercase(Locale.getDefault()) ?: "",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = 36.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        )
        Text(
            text = mentor.name,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider(modifier = Modifier.padding(16.dp))
        Text(
            text = mentor.title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Text(
            text = mentor.description ?: "",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
private fun MentorContent(content: Content) {
    Row(
        modifier = Modifier
            .clickable(onClick = { /* todo */ })
            .padding(vertical = 16.dp)
    ) {
        NetworkImage(
            url = content.thumbnail,
            contentDescription = null,
            modifier = Modifier.size(112.dp, 64.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = content.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.PlayCircleOutline,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = content.subtitle,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
        Text(
            text = content.extra,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}


private enum class SheetState { Open, Closed }

private val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0


@Preview
@Composable
private fun MentorPreview(
    @PreviewParameter(MentorPreviewParameterProvider::class, limit = 1) mentor: Mentor
) {
    AppTheme {
        MentorView(
            modifier = Modifier,
            mentor = mentor
        ) {}
    }
}

@Preview
@Composable
private fun MentorHeaderPreview(
    @PreviewParameter(MentorPreviewParameterProvider::class, limit = 1) mentor: Mentor
) {
    AppTheme {
        MentorHeader(
            mentor = mentor
        ) {}
    }
}

@Preview
@Composable
private fun MentorBodyPreview(
    @PreviewParameter(MentorPreviewParameterProvider::class, limit = 1) mentor: Mentor
) {
    AppTheme {
        MentorBody(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            mentor = mentor
        )
    }
}