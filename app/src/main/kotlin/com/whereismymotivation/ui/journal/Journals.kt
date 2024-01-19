package com.whereismymotivation.ui.journal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereismymotivation.R
import com.whereismymotivation.data.local.db.entity.Journal
import com.whereismymotivation.ui.common.list.InfiniteLazyColumn
import com.whereismymotivation.ui.common.preview.JournalParameterProvider
import com.whereismymotivation.ui.theme.AppTheme
import com.whereismymotivation.utils.common.CalendarUtils

@Composable
fun Journals(modifier: Modifier = Modifier, viewModel: JournalsViewModel) {
    JournalsView(
        modifier = modifier,
        journals = viewModel.journals,
        loadMore = { viewModel.loadMore() },
        delete = { viewModel.delete(it) },
        select = { viewModel.select(it) },
        text = viewModel.journalText.collectAsStateWithLifecycle().value,
        textChange = { viewModel.textChange(it) },
        record = { viewModel.record() }
    )
}

@Composable
private fun JournalsView(
    modifier: Modifier,
    journals: List<Journal>,
    loadMore: () -> Unit,
    delete: (Journal) -> Unit,
    select: (Journal) -> Unit,
    text: String,
    textChange: (String) -> Unit,
    record: () -> Unit
) {
    InfiniteLazyColumn(
        loadMore = loadMore,
        modifier = modifier.fillMaxSize(),
        content = {
            item(key = "WriteJournal") {
                WriteJournal(
                    text = text,
                    textChange = textChange,
                    record = record
                )
                if (journals.isNotEmpty()) Divider()
            }
            items(journals, key = { it.id }) { journal ->
                JournalItem(
                    journal = journal,
                    delete = delete,
                    select = select
                )
                Divider()
            }
        },
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun WriteJournal(
    text: String,
    textChange: (String) -> Unit,
    record: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.note_down_your_best_thing_for_today_here),
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 16.dp),
                value = text,
                minLines = 5,
                maxLines = 30,
                onValueChange = textChange,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        record()
                    }
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.hint_node_down_your_best_thing_for_today_here)
                    )
                },
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = text.isNotBlank(),
                onClick = record
            ) {
                Text(
                    text = stringResource(id = R.string.record)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun JournalItem(
    journal: Journal,
    delete: (Journal) -> Unit,
    select: (Journal) -> Unit
) {
    val isDelete = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { select(journal) },
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
                text = journal.text,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                text = CalendarUtils.getFormattedDate(journal.createdAt) ?: "",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Box(
            modifier = Modifier.size(56.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = RoundedCornerShape(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarMonth,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier
                        .wrapContentSize()
                        .size(42.dp)
                )
            }
            if (isDelete.value) {
                Surface(
                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = .85f),
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { delete(journal) },
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
private fun JournalsPreview(
    @PreviewParameter(JournalParameterProvider::class, limit = 1) journal: Journal
) {
    AppTheme {
        JournalsView(
            modifier = Modifier,
            loadMore = {},
            delete = {},
            select = {},
            text = "",
            textChange = {},
            record = {},
            journals = listOf(
                journal.copy(id = 1),
                journal.copy(id = 2),
                journal.copy(id = 3),
                journal.copy(id = 4),
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JournalItemPreview(
    @PreviewParameter(JournalParameterProvider::class, limit = 1) journal: Journal
) {
    AppTheme {
        JournalItem(
            journal = journal,
            delete = {},
            select = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WriteJournalPreview() {
    AppTheme {
        WriteJournal(
            text = "",
            textChange = {},
            record = {}
        )
    }
}