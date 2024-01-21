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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.Role
import com.whereismymotivation.data.model.User
import com.whereismymotivation.ui.common.appbar.LogoAppBar
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.list.InfiniteLazyColumn
import com.whereismymotivation.ui.common.preview.ContentUserPreviewParameterProvider
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun MyBox(modifier: Modifier, viewModel: MyBoxViewModel) {
    val contents = viewModel.contents
    val user = viewModel.user.collectAsStateWithLifecycle().value

    if (contents.isEmpty()) {
        EmptyView(modifier)
    } else {
        MyBoxView(
            modifier = modifier,
            user = user,
            contents = contents,
            loadMore = { viewModel.loadMore() },
            delete = { viewModel.delete(it) },
            select = { viewModel.select(it) },
            submit = { viewModel.submit(it) },
            unsubmit = { viewModel.unsubmit(it) },
            publish = { viewModel.publish(it) },
            unpublish = { viewModel.unpublish(it) },
        )
    }
}

@Composable
fun MyBoxView(
    modifier: Modifier,
    user: User,
    contents: List<Content>,
    loadMore: () -> Unit,
    delete: (Content) -> Unit,
    select: (Content) -> Unit,
    submit: (Content) -> Unit,
    unsubmit: (Content) -> Unit,
    publish: (Content) -> Unit,
    unpublish: (Content) -> Unit,
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
                    user = user,
                    content = content,
                    delete = delete,
                    select = select,
                    submit = submit,
                    unsubmit = unsubmit,
                    publish = publish,
                    unpublish = unpublish,
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
    user: User,
    content: Content,
    delete: (Content) -> Unit,
    select: (Content) -> Unit,
    submit: (Content) -> Unit,
    unsubmit: (Content) -> Unit,
    publish: (Content) -> Unit,
    unpublish: (Content) -> Unit,
) {
    val isDelete = remember { mutableStateOf(false) }
    val isAdmin = remember { user.roles.find { it.code == Role.RoleCode.ADMIN } != null }

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
                if (isAdmin) {
                    AdminDropDownMenu(
                        content = content,
                        submit = submit,
                        unsubmit = unsubmit,
                        publish = publish,
                        unpublish = unpublish,
                    )
                }
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(vertical = 8.dp),
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
                        .size(86.dp)
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

@Composable
private fun AdminDropDownMenu(
    content: Content,
    submit: (Content) -> Unit,
    unsubmit: (Content) -> Unit,
    publish: (Content) -> Unit,
    unpublish: (Content) -> Unit,
) {
    var menuExpanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.padding(end = 8.dp)
    ) {
        IconButton(onClick = { menuExpanded = true }) {
            Icon(
                imageVector = Icons.Filled.MoreHoriz,
                contentDescription = null
            )
        }
        DropdownMenu(
            modifier = Modifier,
            expanded = menuExpanded,
            offset = DpOffset(0.dp, 0.dp),
            onDismissRequest = { menuExpanded = false },
        ) {
            if (content.submit == true) {
                DropdownMenuItem(
                    onClick = {
                        menuExpanded = false
                        unsubmit(content)
                    },
                    text = {
                        Text(
                            text = "Remove Submission",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )
            } else {
                DropdownMenuItem(
                    onClick = {
                        menuExpanded = false
                        submit(content)
                    },
                    text = {
                        Text(
                            text = "Submit",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )
            }
            if (content.private == true) {
                DropdownMenuItem(
                    onClick = {
                        menuExpanded = false
                        publish(content)
                    },
                    text = {
                        Text(
                            text = "Publish",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                )
            } else {
                DropdownMenuItem(
                    onClick = {
                        menuExpanded = false
                        unpublish(content)
                    },
                    text = {
                        Text(
                            text = "Remove Publish",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MyBoxViewPreview(
    @PreviewParameter(
        ContentUserPreviewParameterProvider::class,
        limit = 1
    ) contentAndUser: Pair<Content, User>,
) {
    val content = contentAndUser.first
    val user = contentAndUser.second
    AppTheme {
        MyBoxView(
            modifier = Modifier,
            loadMore = {},
            delete = {},
            select = {},
            submit = {},
            unsubmit = {},
            publish = {},
            unpublish = {},
            user = user,
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
private fun MyContentAdminPreview(
    @PreviewParameter(
        ContentUserPreviewParameterProvider::class,
        limit = 1
    ) contentAndUser: Pair<Content, User>,
) {
    val content = contentAndUser.first
    val user = contentAndUser.second
    AppTheme {
        MyContent(
            user = user,
            content = content,
            delete = {},
            select = {},
            submit = {},
            unsubmit = {},
            publish = {},
            unpublish = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyContentPreview(
    @PreviewParameter(
        ContentUserPreviewParameterProvider::class,
        limit = 1
    ) contentAndUser: Pair<Content, User>,
) {
    val content = contentAndUser.first
    val user = contentAndUser.second.copy(roles = emptyList())
    AppTheme {
        MyContent(
            user = user,
            content = content,
            delete = {},
            select = {},
            submit = {},
            unsubmit = {},
            publish = {},
            unpublish = {},
        )
    }
}