package com.whereismymotivation.ui.mentors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.ui.common.appbar.NavAppBar
import com.whereismymotivation.ui.common.list.InfiniteLazyColumn
import com.whereismymotivation.ui.common.preview.MentorPreviewParameterProvider
import com.whereismymotivation.ui.common.progress.LoadingPlaceholder
import com.whereismymotivation.ui.onboarding.MentorChip
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun ExploreMentors(modifier: Modifier, viewModel: ExploreMentorsViewModel) {
    val mentors = viewModel.mentors

    LoadingPlaceholder(loading = mentors.isEmpty())

    ExploreMentorsView(
        modifier = modifier,
        mentors = mentors,
        select = { viewModel.mentorSelect(it) },
        unselect = { viewModel.mentorUnselect(it) },
        loadMore = { viewModel.loadMore() },
        upPress = { viewModel.upPress() },
        complete = { viewModel.complete() }
    )
}

@Composable
private fun ExploreMentorsView(
    modifier: Modifier,
    mentors: List<Mentor>,
    select: (Mentor) -> Unit,
    unselect: (Mentor) -> Unit,
    loadMore: () -> Unit,
    upPress: () -> Unit,
    complete: () -> Unit
) {

    if (mentors.isEmpty()) {
        return LoadingPlaceholder(loading = true)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            NavAppBar(
                title = stringResource(R.string.explore_mentors),
                upPress = upPress
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = complete,
                modifier = Modifier,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Rounded.Done,
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        InfiniteLazyColumn(
            loadMore = loadMore,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        ) {
            items(mentors, key = { it.id }) { mentor ->
                MentorChip(
                    mentor = mentor,
                    fullWidth = true
                ) {
                    if (it) select(mentor)
                    else unselect(mentor)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExploreMentorsPreview(
    @PreviewParameter(MentorPreviewParameterProvider::class, limit = 1) mentor: Mentor,
) {
    AppTheme {
        ExploreMentorsView(
            modifier = Modifier,
            select = {},
            unselect = {},
            mentors = listOf(
                mentor.copy(id = "1", subscribed = true),
                mentor.copy(id = "2"),
                mentor.copy(id = "3"),
                mentor.copy(id = "4"),
                mentor.copy(id = "5"),
                mentor.copy(id = "6"),
                mentor.copy(id = "7")
            ),
            loadMore = {},
            upPress = {},
            complete = {}
        )
    }
}