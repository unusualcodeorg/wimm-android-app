package com.whereismymotivation.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.list.InfiniteLazyColumn
import com.whereismymotivation.ui.common.preview.ContentPreviewParameterProvider
import com.whereismymotivation.ui.common.progress.LoadingPlaceholder
import com.whereismymotivation.ui.theme.AppTheme
import com.whereismymotivation.ui.theme.black
import com.whereismymotivation.ui.theme.white

@Composable
fun YouTubeContent(modifier: Modifier = Modifier, viewModel: YoutubeViewModel) {
    val content = viewModel.content.collectAsStateWithLifecycle().value
        ?: return LoadingPlaceholder(loading = true)

    val similarContents = viewModel.similarContents

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        YouTubePlayer(url = content.extra)
        YouTubeContentView(
            content = content,
            similarContents = similarContents,
            selectSimilarContent = { viewModel.selectSimilarContent(it) },
            likeClick = { viewModel.toggleContentLike(it) },
            shareClick = { viewModel.shareContent(it) },
            whatsAppClick = { viewModel.shareWhatsappContent(it) },
            loadMoreSimilarContents = { viewModel.loadMoreSimilarContents() },
        )
    }
}

@Composable
private fun YouTubeContentView(
    modifier: Modifier = Modifier,
    content: Content,
    similarContents: List<Content>,
    selectSimilarContent: (Content) -> Unit,
    likeClick: (Content) -> Unit,
    shareClick: (Content) -> Unit,
    whatsAppClick: (Content) -> Unit,
    loadMoreSimilarContents: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        ContentActions(
            content = content,
            likeClick = likeClick,
            shareClick = shareClick,
            whatsAppClick = whatsAppClick,
        )
        SimilarContents(
            modifier = Modifier,
            contents = similarContents,
            selectContent = selectSimilarContent,
            loadMore = { loadMoreSimilarContents() }
        )
    }
}

@Composable
private fun ContentActions(
    modifier: Modifier = Modifier,
    content: Content,
    likeClick: (Content) -> Unit,
    shareClick: (Content) -> Unit,
    whatsAppClick: (Content) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.background(MaterialTheme.colorScheme.black)) {
            Box(modifier = Modifier.weight(1f)) {
                IconButton(
                    onClick = {},
                    modifier = modifier
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.white,
                            shape = CircleShape
                        )
                        .size(28.dp)

                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(R.drawable.ic_box),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.black,
                    )

                }
            }
            IconButton(
                onClick = { likeClick(content) },
                modifier = modifier
                    .padding(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.white,
                        shape = CircleShape
                    )
                    .size(28.dp)

            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = if (content.liked == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if (content.liked == true) Color.Red else Color.Black,
                )
            }
            IconButton(
                onClick = { shareClick(content) },
                modifier = modifier
                    .padding(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.white,
                        shape = CircleShape
                    )
                    .size(28.dp)

            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
            IconButton(
                onClick = { whatsAppClick(content) },
                modifier = modifier
                    .padding(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.white,
                        shape = CircleShape
                    )
                    .size(28.dp)

            ) {
                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(R.drawable.ic_whatsapp),
                    contentDescription = "whatsapp",
                )
            }
        }
    }
}

@Composable
private fun SimilarContents(
    modifier: Modifier = Modifier,
    contents: List<Content>,
    selectContent: (Content) -> Unit,
    loadMore: () -> Unit
) {
    InfiniteLazyColumn(loadMore = loadMore) {
        items(contents) { content ->
            SimilarContent(modifier, content, selectContent)
            Divider()
        }
    }
}

@Composable
private fun SimilarContent(
    modifier: Modifier = Modifier,
    content: Content,
    selectContent: (Content) -> Unit
) {
    Row(
        modifier = modifier
            .clickable(onClick = { selectContent(content) })
            .padding(16.dp)
    ) {
        NetworkImage(
            url = content.thumbnail,
            contentDescription = null,
            modifier = Modifier
                .size(112.dp, 64.dp)
        )
        Column(
            modifier = Modifier.padding(start = 16.dp)
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
    }
}


@Preview
@Composable
private fun YouTubeContentPreview(
    @PreviewParameter(ContentPreviewParameterProvider::class, limit = 1) content: Content
) {
    AppTheme(dark = false) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            YouTubePlaceholder()
            YouTubeContentView(
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                content = content.copy(),
                selectSimilarContent = {},
                similarContents = listOf(
                    content.copy(),
                    content.copy(),
                    content.copy(),
                    content.copy(),
                    content.copy(),
                    content.copy(),
                ),
                loadMoreSimilarContents = {},
                likeClick = {},
                shareClick = {},
                whatsAppClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun SimilarContentsPreview(
    @PreviewParameter(ContentPreviewParameterProvider::class, limit = 1) content: Content
) {
    AppTheme {
        SimilarContents(
            Modifier.background(MaterialTheme.colorScheme.background),
            selectContent = {},
            contents = listOf(
                content.copy(),
                content.copy(),
                content.copy(),
                content.copy(),
                content.copy(),
                content.copy(),
            ),
            loadMore = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SimilarContentPreview(
    @PreviewParameter(ContentPreviewParameterProvider::class, limit = 1) content: Content
) {
    AppTheme {
        SimilarContent(
            content = content,
            selectContent = {}
        )
    }
}
