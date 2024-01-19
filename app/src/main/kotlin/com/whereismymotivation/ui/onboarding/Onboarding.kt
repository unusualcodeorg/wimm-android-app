package com.whereismymotivation.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.DoneAll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.whereismymotivation.R
import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.ui.common.appbar.LogoAppBar
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.preview.MentorPreviewParameterProvider
import com.whereismymotivation.ui.common.preview.TopicMentorPreviewParameterProvider
import com.whereismymotivation.ui.common.preview.TopicPreviewParameterProvider
import com.whereismymotivation.ui.common.progress.LoadingPlaceholder
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun Onboarding(modifier: Modifier, viewModel: OnboardingViewModel) {
    val topics = viewModel.topics
    val mentors = viewModel.mentors
    OnboardingView(
        modifier = modifier,
        topics = topics,
        topicSelect = { viewModel.topicSelect(it) },
        topicUnselect = { viewModel.topicUnselect(it) },
        mentors = mentors,
        mentorSelect = { viewModel.mentorSelect(it) },
        mentorUnselect = { viewModel.mentorUnselect(it) },
        complete = { viewModel.complete() }
    )
}

@Composable
private fun OnboardingView(
    modifier: Modifier,
    topics: List<Topic>,
    topicSelect: (Topic) -> Unit,
    topicUnselect: (Topic) -> Unit,
    mentors: List<Mentor>,
    mentorSelect: (Mentor) -> Unit,
    mentorUnselect: (Mentor) -> Unit,
    complete: () -> Unit
) {

    if (topics.isEmpty() && mentors.isEmpty()) {
        return LoadingPlaceholder(loading = true)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { LogoAppBar(title = stringResource(R.string.app_name)) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = complete,
                modifier = Modifier,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Rounded.DoneAll,
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(
                text = stringResource(R.string.interest_selection_title),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 32.dp
                )
            )

            TopicsGrid(
                topics = topics,
                select = topicSelect,
                unselect = topicUnselect
            )

            Text(
                text = stringResource(R.string.mentor_selection_title),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 32.dp
                )
            )

            MentorsGrid(
                mentors = mentors,
                select = mentorSelect,
                unselect = mentorUnselect
            )

            Spacer(Modifier.height(56.dp))
        }
    }
}

@Composable
private fun TopicsGrid(
    modifier: Modifier = Modifier,
    topics: List<Topic>,
    select: (Topic) -> Unit,
    unselect: (Topic) -> Unit,
) {
    val state = rememberLazyStaggeredGridState()

    LazyHorizontalStaggeredGrid(
        state = state,
        modifier = modifier
            .fillMaxWidth()
            .height((96 * 2).dp),
        rows = StaggeredGridCells.Fixed(2),
        horizontalItemSpacing = 2.dp,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
    ) {
        items(topics, key = { it.id }) { topic ->
            TopicChip(topic) {
                if (it) select(topic)
                else unselect(topic)
            }
        }
    }
}

@Composable
private fun MentorsGrid(
    modifier: Modifier = Modifier,
    mentors: List<Mentor>,
    select: (Mentor) -> Unit,
    unselect: (Mentor) -> Unit,
) {
    val state = rememberLazyStaggeredGridState()

    LazyHorizontalStaggeredGrid(
        state = state,
        modifier = modifier
            .fillMaxWidth()
            .height((96 * 2).dp),
        rows = StaggeredGridCells.Fixed(2),
        horizontalItemSpacing = 2.dp,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
    ){
        items(mentors, key = { it.id }) { mentor ->
            MentorChip(mentor) {
                if (it) select(mentor)
                else unselect(mentor)
            }
        }
    }
}

@Composable
fun MentorChip(
    mentor: Mentor,
    fullWidth: Boolean = false,
    select: (Boolean) -> Unit,
) {
    SelectionChip(
        thumbnail = mentor.thumbnail,
        title = mentor.name,
        subtitle = mentor.occupation,
        selected = mentor.subscribed ?: false,
        select = select,
        iconId = R.drawable.ic_mentor,
        fullWidth = fullWidth
    )
}

@Composable
fun TopicChip(
    topic: Topic,
    fullWidth: Boolean = false,
    select: (Boolean) -> Unit,
) {
    SelectionChip(
        thumbnail = topic.thumbnail,
        title = topic.name,
        subtitle = topic.title,
        selected = topic.subscribed ?: false,
        select = select,
        iconId = R.drawable.ic_topic,
        fullWidth = fullWidth
    )
}

@Composable
private fun SelectionChip(
    thumbnail: String,
    title: String,
    subtitle: String,
    selected: Boolean,
    select: (Boolean) -> Unit,
    @DrawableRes iconId: Int,
    fullWidth: Boolean
) {
    val chipTransitionState = chipTransition(selected)

    Surface(
        modifier = Modifier
            .padding(4.dp)
            .run {
                if (fullWidth) fillMaxWidth()
                else widthIn(154.dp, 254.dp)
            },
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(chipTransitionState.cornerRadius),
    ) {
        Row(modifier = Modifier.toggleable(value = selected, onValueChange = select)) {
            Box {
                NetworkImage(
                    url = thumbnail,
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 86.dp, height = 86.dp)
                        .aspectRatio(1f)
                )
                if (chipTransitionState.selectedAlpha > 0f) {
                    Surface(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = chipTransitionState.selectedAlpha),
                        modifier = Modifier.matchParentSize()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary.copy(
                                alpha = chipTransitionState.selectedAlpha
                            ),
                            modifier = Modifier
                                .wrapContentSize()
                                .scale(chipTransitionState.checkScale)
                        )
                    }
                }
            }
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
                        Icon(
                            painter = painterResource(id = iconId),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(12.dp)
                        )
                        Text(
                            text = subtitle,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(start = 8.dp, end = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun chipTransition(topicSelected: Boolean): ChipTransition {
    val transition = updateTransition(
        targetState = topicSelected,
        label = "",
    )
    val cornerRadius = transition.animateDp(label = "") { state ->
        when (state) {
            false -> 0.dp
            true -> 12.dp
        }
    }
    val selectedAlpha = transition.animateFloat(label = "") { state ->
        when (state) {
            false -> 0f
            true -> 0.8f
        }
    }
    val checkScale = transition.animateFloat(label = "") { state ->
        when (state) {
            false -> 0.6f
            true -> 1f
        }
    }
    return remember(transition) {
        ChipTransition(cornerRadius, selectedAlpha, checkScale)
    }
}

/**
 * Class holding animating values when transitioning topic chip states.
 */
private class ChipTransition(
    cornerRadius: State<Dp>,
    selectedAlpha: State<Float>,
    checkScale: State<Float>
) {
    val cornerRadius by cornerRadius
    val selectedAlpha by selectedAlpha
    val checkScale by checkScale
}

@Preview(showBackground = true)
@Composable
private fun OnboardingPreview(
    @PreviewParameter(
        TopicMentorPreviewParameterProvider::class,
        limit = 1
    ) topicAndMentor: Pair<Topic, Mentor>,
) {
    val topic = topicAndMentor.first
    val mentor = topicAndMentor.second
    AppTheme {
        OnboardingView(
            modifier = Modifier,
            topics = listOf(
                topic.copy(id = "1", subscribed = true),
                topic.copy(id = "2"),
                topic.copy(id = "3"),
                topic.copy(id = "4"),
                topic.copy(id = "5"),
                topic.copy(id = "6"),
                topic.copy(id = "7"),
                topic.copy(id = "8"),
            ),
            topicSelect = {},
            topicUnselect = {},
            mentors = listOf(
                mentor.copy(id = "1"),
                mentor.copy(id = "2", subscribed = true),
                mentor.copy(id = "3"),
                mentor.copy(id = "4"),
                mentor.copy(id = "5"),
                mentor.copy(id = "6"),
                mentor.copy(id = "7"),
            ),
            mentorSelect = {},
            mentorUnselect = {},
            complete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TopicsGridPreview(
    @PreviewParameter(TopicPreviewParameterProvider::class, limit = 1) topic: Topic,
) {
    AppTheme {
        TopicsGrid(
            select = {},
            unselect = {},
            topics = listOf(
                topic.copy(id = "1", subscribed = true),
                topic.copy(id = "2"),
                topic.copy(id = "3"),
                topic.copy(id = "4"),
                topic.copy(id = "5"),
                topic.copy(id = "6"),
                topic.copy(id = "7"),
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MentorsGridPreview(
    @PreviewParameter(MentorPreviewParameterProvider::class, limit = 1) mentor: Mentor,
) {
    AppTheme {
        MentorsGrid(
            select = {},
            unselect = {},
            mentors = listOf(
                mentor.copy(id = "1"),
                mentor.copy(id = "2", subscribed = true),
                mentor.copy(id = "3"),
                mentor.copy(id = "4"),
                mentor.copy(id = "5"),
                mentor.copy(id = "6"),
                mentor.copy(id = "7"),
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TopicChipPreview(
    @PreviewParameter(TopicPreviewParameterProvider::class, limit = 1) topic: Topic,
) {
    AppTheme {
        TopicChip(
            topic = topic.copy(),
            select = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MentorChipPreview(
    @PreviewParameter(MentorPreviewParameterProvider::class, limit = 1) mentor: Mentor,
) {
    AppTheme {
        MentorChip(
            mentor = mentor.copy(),
            select = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingEmptyPreview() {
    AppTheme {
        OnboardingView(
            modifier = Modifier,
            topics = emptyList(),
            topicSelect = {},
            topicUnselect = {},
            mentors = emptyList(),
            mentorSelect = {},
            mentorUnselect = {},
            complete = {}
        )
    }
}