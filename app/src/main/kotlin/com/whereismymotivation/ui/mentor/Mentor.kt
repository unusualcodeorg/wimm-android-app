package com.whereismymotivation.ui.mentor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Article
import androidx.compose.material.icons.rounded.Audiotrack
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material.icons.rounded.Topic
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.ui.common.appbar.LogoUpAppBar
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.image.OutlinedAvatar
import com.whereismymotivation.ui.common.preview.ContentPreviewParameterProvider
import com.whereismymotivation.ui.common.preview.MentorPreviewParameterProvider
import com.whereismymotivation.ui.common.progress.LoadingPlaceholder
import com.whereismymotivation.ui.common.utils.scrim
import com.whereismymotivation.ui.theme.AppTheme
import com.whereismymotivation.ui.theme.white
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun Mentor(modifier: Modifier, viewModel: MentorViewModel) {
    val mentor = viewModel.mentor.collectAsStateWithLifecycle().value
        ?: return LoadingPlaceholder(loading = true)

    val contents = viewModel.contents.collectAsStateWithLifecycle().value

    MentorView(
        modifier = modifier.fillMaxSize(),
        mentor = mentor,
        contents = contents,
        selectContent = { viewModel.selectContent(it) },
        upPress = { viewModel.upPress() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MentorView(
    modifier: Modifier,
    mentor: Mentor,
    contents: List<Content>?,
    selectContent: (Content) -> Unit,
    upPress: () -> Unit,
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 56.dp,
        sheetContainerColor = MaterialTheme.colorScheme.primary,
        sheetContent = {
            if (contents != null) MentorContents(
                contents = contents,
                selectContent = selectContent
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                    }
                ) {
                    Text("Close")
                }
            }
        })
    { innerPadding ->
        MentorDescription(
            modifier = Modifier.padding(innerPadding),
            mentor = mentor,
            upPress = upPress
        )
    }
}

@Composable
private fun MentorDescription(
    modifier: Modifier = Modifier,
    mentor: Mentor,
    upPress: () -> Unit
) {
    Surface(modifier = modifier.fillMaxSize()) {
        LazyColumn {
            item { MentorHeader(mentor, upPress) }
            item { MentorBody(Modifier, mentor) }
        }
    }
}

@Composable
private fun MentorHeader(
    mentor: Mentor,
    upPress: () -> Unit
) {
    Box {
        NetworkImage(
            url = mentor.coverImgUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .scrim(colors = listOf(Color(0x80000000), Color(0x33000000)))
                .aspectRatio(4f / 3f)
        )
        LogoUpAppBar(
            contentColor = MaterialTheme.colorScheme.white,
            upPress = upPress
        )
        OutlinedAvatar(
            url = mentor.thumbnail,
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomCenter)
                .offset(y = 40.dp) // overlap bottom of image
        )
    }
}

@Composable
private fun MentorBody(modifier: Modifier = Modifier, mentor: Mentor) {
    Column(modifier = modifier.padding(bottom = 42.dp)) {
        Text(
            text = mentor.occupation.uppercase(Locale.getDefault()),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = 64.dp,
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
private fun MentorContents(
    modifier: Modifier = Modifier,
    contents: List<Content>,
    selectContent: (Content) -> Unit,
) {
    LazyColumn {
        items(contents, key = { it.id }) { content ->
            MentorContent(modifier, content, selectContent)
            Divider(modifier = Modifier.padding(start = 120.dp))
        }
    }
}

@Composable
fun MentorContent(
    modifier: Modifier = Modifier,
    content: Content,
    selectContent: (Content) -> Unit,
) {
    Row(
        modifier = modifier
            .clickable(onClick = { selectContent(content) })
            .padding(vertical = 16.dp)
    ) {
        NetworkImage(
            url = content.thumbnail,
            contentDescription = null,
            modifier = Modifier
                .size(112.dp, 64.dp)
                .padding(start = 8.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = content.title,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = content.subtitle,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Icon(
            imageVector = when (content.category) {
                Content.Category.AUDIO -> Icons.Rounded.Audiotrack
                Content.Category.VIDEO -> Icons.Rounded.PlayCircleOutline
                Content.Category.IMAGE -> Icons.Rounded.Image
                Content.Category.YOUTUBE -> Icons.Rounded.PlayCircleOutline
                Content.Category.FACEBOOK_VIDEO -> Icons.Rounded.PlayCircleOutline
                Content.Category.ARTICLE -> Icons.Rounded.Article
                Content.Category.QUOTE -> Icons.Rounded.FormatQuote
                Content.Category.MENTOR_INFO -> Icons.Rounded.PersonOutline
                Content.Category.TOPIC_INFO -> Icons.Rounded.Topic
            },
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MentorPreview(
    @PreviewParameter(MentorPreviewParameterProvider::class, limit = 1) mentor: Mentor
) {
    AppTheme {
        MentorView(
            modifier = Modifier,
            mentor = mentor,
            contents = emptyList(),
            selectContent = {},
            upPress = {},
        )
    }
}

@Preview(showBackground = true)
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

@Preview(showBackground = true)
@Composable
private fun MentorBodyPreview(
    @PreviewParameter(MentorPreviewParameterProvider::class, limit = 1) mentor: Mentor
) {
    AppTheme {
        MentorBody(mentor = mentor)
    }
}

@Preview(showBackground = true)
@Composable
private fun MentorContentPreview(
    @PreviewParameter(ContentPreviewParameterProvider::class, limit = 1) content: Content
) {
    AppTheme {
        MentorContent(
            content = content,
            selectContent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MentorContentsPreview(
    @PreviewParameter(ContentPreviewParameterProvider::class, limit = 1) content: Content,
) {
    AppTheme {
        MentorContents(
            selectContent = {},
            contents = listOf(
                content.copy(id = "1"),
                content.copy(id = "2"),
                content.copy(id = "3"),
                content.copy(id = "4"),
                content.copy(id = "5"),
                content.copy(id = "6")
            ),
        )
    }
}