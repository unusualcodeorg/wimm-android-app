package com.whereismymotivation.ui.mybox

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.ui.common.appbar.LogoAppBar
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.list.InfiniteLazyColumn
import com.whereismymotivation.ui.common.preview.ContentPreviewParameterProvider
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun MyBox(modifier: Modifier, viewModel: MyBoxViewModel) {
    val contents = viewModel.contents

    if (contents.isEmpty()) {
        EmptyView(modifier)
    } else {
        MyBoxView(
            modifier = modifier,
            contents = contents,
            loadMore = { viewModel.loadMore() },
            delete = { viewModel.delete(it) },
            select = { viewModel.select(it) }
        )
    }
}

@Composable
fun MyBoxView(
    modifier: Modifier,
    contents: List<Content>,
    loadMore: () -> Unit,
    delete: (Content) -> Unit,
    select: (Content) -> Unit
) {
    InfiniteLazyColumn(
        loadMore = loadMore,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth(),
        content = {
            item(key = "LogoAppBar") {
                LogoAppBar(
                    title = stringResource(R.string.menu_box)
                )
            }
            items(contents, key = { it.id }) { content ->
                MyContent(
                    content = content,
                    delete = delete,
                    select = select
                )
                Divider()
            }
        },
    )
}

@Composable
private fun EmptyView(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Icon(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally),
                imageVector = Icons.Default.Share,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.3f
                )
            )
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = stringResource(id = R.string.motivation_box_empty_state_title),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = stringResource(id = R.string.motivation_box_empty_state_message),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MyContent(
    content: Content,
    delete: (Content) -> Unit,
    select: (Content) -> Unit
) {
    val isDelete = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { select(content) },
                onLongClick = { isDelete.value = !isDelete.value }
            )
            .padding(8.dp)

    ) {
        Column(
            Modifier
                .height(IntrinsicSize.Min)
                .weight(1f)
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(
                text = content.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            Row {
                IconButton(
                    onClick = { /* Handle popup menu button click */ },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreHoriz,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 8.dp),
                    text = content.subtitle,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

        }
        Box {
            Surface(
                shape = RoundedCornerShape(6.dp)
            ) {
                NetworkImage(
                    url = content.thumbnail,
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 86.dp, height = 86.dp)
                        .aspectRatio(1f)
                )
            }
            if (isDelete.value) {
                Surface(
                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = .85f),
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { delete(content) },
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.DeleteForever,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.inversePrimary,
                        modifier = Modifier
                            .wrapContentSize()
                            .size(42.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyBoxViewPreview(
    @PreviewParameter(ContentPreviewParameterProvider::class, limit = 1) content: Content
) {
    AppTheme {
        MyBoxView(
            modifier = Modifier,
            loadMore = {},
            delete = {},
            select = {},
            contents = listOf(
                content.copy(id = "1"),
                content.copy(id = "2"),
                content.copy(id = "3"),
                content.copy(id = "4"),
                content.copy(id = "5"),
                content.copy(id = "6"),
                content.copy(id = "7"),
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyPreview() {
    AppTheme {
        EmptyView(modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun MyContentPreview(
    @PreviewParameter(ContentPreviewParameterProvider::class, limit = 1) content: Content
) {
    AppTheme {
        MyContent(
            content = content,
            delete = {},
            select = {}
        )
    }
}