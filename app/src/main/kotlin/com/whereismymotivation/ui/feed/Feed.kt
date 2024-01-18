package com.whereismymotivation.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.ui.common.appbar.LogoAppBar
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.list.InfiniteLazyColumn
import com.whereismymotivation.ui.common.preview.ContentPreviewParameterProvider
import com.whereismymotivation.ui.common.progress.LoadingPlaceholder
import com.whereismymotivation.ui.mentor.MentorContent
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun Feed(modifier: Modifier, viewModel: FeedViewModel) {
    LoadingPlaceholder(loading = viewModel.contents.isEmpty())
    FeedView(
        modifier = modifier,
        contents = viewModel.contents,
        cardClick = { viewModel.selectContent(it) },
        profileClick = { },
        likeClick = { viewModel.toggleContentLike(it) },
        shareClick = { viewModel.shareContent(it) },
        whatsAppClick = { viewModel.shareWhatsappContent(it) },
        loadMore = { viewModel.loadMoreContents() }
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
        extraItemsCount = 1
    ) {
        item(key = "LogoAppBar") {
            LogoAppBar(title = stringResource(R.string.app_name))
        }
        items(contents) { content ->
            FeedContentItem(
                content = content,
                cardClick = cardClick,
                profileClick = profileClick,
                likeClick = likeClick,
                shareClick = shareClick,
                whatsAppClick = whatsAppClick
            ) {
                when (content.category) {
                    Content.Category.AUDIO,
                    Content.Category.VIDEO,
                    Content.Category.IMAGE,
                    Content.Category.YOUTUBE,
                    Content.Category.FACEBOOK_VIDEO,
                    Content.Category.ARTICLE,
                    Content.Category.MENTOR_INFO,
                    Content.Category.TOPIC_INFO ->
                        NetworkImage(
                            url = content.thumbnail,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.height(220.dp)
                        )

                    Content.Category.QUOTE -> FeedQuote(
                        saying = content.extra,
                        author = content.subtitle
                    )
                }
            }
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
                content.copy(
                    category = Content.Category.QUOTE,
                    extra = "Don't let what you cannot do interfere with what you can do.",
                    subtitle = "Anonymous"
                ),
                content.copy(),
                content.copy()
            ),
        )
    }
}