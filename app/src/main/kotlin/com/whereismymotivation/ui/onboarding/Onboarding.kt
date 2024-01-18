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
import androidx.compose.material.icons.rounded.Explore
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
    val topicSuggestions = viewModel.suggestionTopics
    val mentorSuggestions = viewModel.suggestionMentors
    OnboardingView(
        modifier = modifier,
        topicSuggestions = topicSuggestions,
        topicSelect = { viewModel.topicSelect(it) },
        topicUnselect = { viewModel.topicUnselect(it) },
        mentorSuggestions = mentorSuggestions,
        mentorSelect = { viewModel.mentorSelect(it) },
        mentorUnselect = { viewModel.mentorUnselect(it) },
        complete = { viewModel.complete() }
    )
}

@Composable
private fun OnboardingView(
    modifier: Modifier,
    topicSuggestions: List<Suggestion<Topic>>,
    topicSelect: (Suggestion<Topic>) -> Unit,
    topicUnselect: (Suggestion<Topic>) -> Unit,
    mentorSuggestions: List<Suggestion<Mentor>>,
    mentorSelect: (Suggestion<Mentor>) -> Unit,
    mentorUnselect: (Suggestion<Mentor>) -> Unit,
    complete: () -> Unit
) {

    if (topicSuggestions.isEmpty() && mentorSuggestions.isEmpty()) {
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
                    imageVector = Icons.Rounded.Explore,
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
                suggestions = topicSuggestions,
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
                suggestions = mentorSuggestions,
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
    suggestions: List<Suggestion<Topic>>,
    select: (Suggestion<Topic>) -> Unit,
    unselect: (Suggestion<Topic>) -> Unit,
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
        content = {
            items(suggestions, key = { it.data.id }) { suggestion ->
                TopicChip(
                    suggestion.data,
                    suggestion.selected
                ) {
                    if (it) select(suggestion)
                    else unselect(suggestion)
                }
            }
        },
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
    )
}

@Composable
private fun MentorsGrid(
    modifier: Modifier = Modifier,
    suggestions: List<Suggestion<Mentor>>,
    select: (Suggestion<Mentor>) -> Unit,
    unselect: (Suggestion<Mentor>) -> Unit,
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
        content = {
            items(suggestions, key = { it.data.id }) { suggestion ->
                MentorChip(
                    suggestion.data,
                    suggestion.selected
                ) {
                    if (it) select(suggestion)
                    else unselect(suggestion)
                }
            }
        },
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
    )
}

@Composable
private fun MentorChip(
    mentor: Mentor,
    selected: Boolean,
    select: (Boolean) -> Unit,
) {
    SelectionChip(
        thumbnail = mentor.thumbnail,
        title = mentor.name,
        subtitle = mentor.occupation,
        selected = selected,
        select = select,
        iconId = R.drawable.ic_mentor
    )
}

@Composable
private fun TopicChip(
    topic: Topic,
    selected: Boolean,
    select: (Boolean) -> Unit
) {
    SelectionChip(
        thumbnail = topic.thumbnail,
        title = topic.name,
        subtitle = topic.title,
        selected = selected,
        select = select,
        iconId = R.drawable.ic_topic
    )
}

@Composable
private fun SelectionChip(
    thumbnail: String,
    title: String,
    subtitle: String,
    selected: Boolean,
    select: (Boolean) -> Unit,
    @DrawableRes iconId: Int
) {
    val chipTransitionState = chipTransition(selected)

    Surface(
        modifier = Modifier
            .padding(4.dp)
            .widthIn(154.dp, 254.dp),
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
            topicSuggestions = listOf(
                Suggestion(topic.copy(id = "1"), true),
                Suggestion(topic.copy(id = "2"), false),
                Suggestion(topic.copy(id = "3"), false),
                Suggestion(topic.copy(id = "4"), false),
                Suggestion(topic.copy(id = "5"), false),
                Suggestion(topic.copy(id = "6"), false),
                Suggestion(topic.copy(id = "7"), false),
            ),
            topicSelect = {},
            topicUnselect = {},
            mentorSuggestions = listOf(
                Suggestion(mentor.copy(id = "1"), true),
                Suggestion(mentor.copy(id = "2"), false),
                Suggestion(mentor.copy(id = "3"), false),
                Suggestion(mentor.copy(id = "4"), false),
                Suggestion(mentor.copy(id = "5"), false),
                Suggestion(mentor.copy(id = "6"), false),
                Suggestion(mentor.copy(id = "7"), false),
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
            suggestions = listOf(
                Suggestion(topic.copy(id = "1"), true),
                Suggestion(topic.copy(id = "2"), false),
                Suggestion(topic.copy(id = "3"), false),
                Suggestion(topic.copy(id = "4"), false),
                Suggestion(topic.copy(id = "5"), false),
                Suggestion(topic.copy(id = "6"), false),
                Suggestion(topic.copy(id = "7"), false),
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
            suggestions = listOf(
                Suggestion(mentor.copy(id = "1"), true),
                Suggestion(mentor.copy(id = "2"), false),
                Suggestion(mentor.copy(id = "3"), false),
                Suggestion(mentor.copy(id = "4"), false),
                Suggestion(mentor.copy(id = "5"), false),
                Suggestion(mentor.copy(id = "6"), false),
                Suggestion(mentor.copy(id = "7"), false),
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
            selected = false,
            select = { }
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
            selected = false,
            select = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingEmptyPreview() {
    AppTheme {
        OnboardingView(
            modifier = Modifier,
            topicSuggestions = emptyList(),
            topicSelect = {},
            topicUnselect = {},
            mentorSuggestions = emptyList(),
            mentorSelect = {},
            mentorUnselect = {},
            complete = {}
        )
    }
}