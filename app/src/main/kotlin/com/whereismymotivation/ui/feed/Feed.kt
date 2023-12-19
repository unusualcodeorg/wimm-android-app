package com.whereismymotivation.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.ui.common.appbar.LogoAppBar
import com.whereismymotivation.ui.common.preview.ContentPreviewParameterProvider
import com.whereismymotivation.ui.common.utils.rememberScrollLastItemVisible
import com.whereismymotivation.ui.mentor.MentorContent
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun Feed(modifier: Modifier, feedViewModel: FeedViewModel) {
    val contents = feedViewModel.contents.collectAsState().value
    FeedView(
        modifier = modifier,
        contents = contents,
        selectContent = { feedViewModel.selectContent(it) },
        loadMore = { feedViewModel.loadMoreContents() }
    )
}

@Composable
fun FeedView(
    modifier: Modifier = Modifier,
    contents: List<Content>,
    selectContent: (Content) -> Unit,
    loadMore: () -> Unit,
) {
    val state = rememberLazyListState()
    val isAtBottom by rememberScrollLastItemVisible(state)

    LazyColumn(
        state = state,
        modifier = modifier.fillMaxSize(),
        content = {
            item(key = "LogoAppBar") {
                LogoAppBar(title = stringResource(R.string.app_name))
            }
            itemsIndexed(contents, key = { _, item -> item.id }) { index, content ->
                FeedItemView(content, selectContent)
                if (index == contents.lastIndex && isAtBottom) {
                    loadMore()
                }
            }
        },
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
    )
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
            selectContent = {},
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