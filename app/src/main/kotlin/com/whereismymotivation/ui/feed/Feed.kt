package com.whereismymotivation.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.ui.common.InfiniteLazyColumn
import com.whereismymotivation.ui.common.appbar.LogoAppBar
import com.whereismymotivation.ui.common.preview.ContentPreviewParameterProvider
import com.whereismymotivation.ui.mentor.MentorContent
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun Feed(modifier: Modifier, feedViewModel: FeedViewModel) {
    val contents = feedViewModel.contents.collectAsState().value
    FeedView(
        modifier = modifier,
        contents = contents,
        cardClick = { },
        profileClick = { },
        likeClick = { feedViewModel.toggleContentLike(it) },
        shareClick = { },
        whatsAppClick = { },
        loadMore = { feedViewModel.loadMoreContents() }
    )
}

@Composable
fun FeedView(
    modifier: Modifier = Modifier,
    contents: List<Content>,
    cardClick: (Content) -> Unit,
    profileClick: (Content) -> Unit,
    likeClick: (Content) -> Unit,
    shareClick: (Content) -> Unit,
    whatsAppClick: (Content) -> Unit,
    loadMore: () -> Unit,
) {
    InfiniteLazyColumn(
        modifier = modifier.fillMaxSize(),
        loadMore = loadMore,
        key = "FeedView",
        extraItemsCount = 1
    ) {
        item(key = "LogoAppBar") {
            LogoAppBar(title = stringResource(R.string.app_name))
            Divider()
        }
        items(contents) { content ->
            FeedContentItem(
                content = content,
                cardClick = cardClick,
                profileClick = profileClick,
                likeClick = likeClick,
                shareClick = shareClick,
                whatsAppClick = whatsAppClick
            )
        }
    }
}

@Composable
private fun FeedItemView(
    content: Content,
    selectContent: (Content) -> Unit
) {
    Column {
        MentorContent(Modifier, content, selectContent)
        Divider(modifier = Modifier.padding(start = 120.dp))
    }
}

@Preview
@Composable
private fun FeedPreview(
    @PreviewParameter(ContentPreviewParameterProvider::class, limit = 1) content: Content
) {
    AppTheme {
        FeedView(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            cardClick = { },
            profileClick = { },
            likeClick = { },
            shareClick = { },
            whatsAppClick = { },
            loadMore = {},
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