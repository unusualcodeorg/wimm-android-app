package com.whereismymotivation.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.preview.TopicPreviewParameterProvider
import com.whereismymotivation.ui.common.progress.LoadingPlaceholder

@Composable
fun Suggestion(modifier: Modifier = Modifier, viewModel: SuggestionViewModel) {
    val topics = viewModel.topics.collectAsStateWithLifecycle().value
    LoadingPlaceholder(loading = topics.isEmpty())
    SuggestionView(
        modifier = modifier,
        topics = topics,
        selectTopic = { viewModel.selectTopic(it) }
    )
}

@Composable
private fun SuggestionView(
    modifier: Modifier = Modifier,
    topics: List<Topic>,
    selectTopic: (Topic) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(topics) {
            TopicSuggestionItem(
                topic = it,
                selectTopic = selectTopic
            )
        }
    }
}

@Composable
private fun TopicSuggestionItem(
    modifier: Modifier = Modifier,
    topic: Topic,
    selectTopic: (Topic) -> Unit
) {
    Surface(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small
    ) {
        Box(
            modifier = Modifier.clickable { selectTopic(topic) }
        ) {
            NetworkImage(
                url = topic.coverImgUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .alpha(0.85f)
            )

            Surface(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
                    .alpha(0.85f),
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.background
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.BottomStart)
                        .alpha(0.7f),
                    text = topic.name,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SuggestionPreview(
    @PreviewParameter(TopicPreviewParameterProvider::class, limit = 1) topic: Topic

) {
    SuggestionView(
        modifier = Modifier,
        topics = listOf(
            topic.copy(),
            topic.copy(),
            topic.copy(),
            topic.copy(),
            topic.copy(),
        ),
        selectTopic = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun TopicSuggestionItemPreview(
    @PreviewParameter(TopicPreviewParameterProvider::class, limit = 1) topic: Topic

) {
    TopicSuggestionItem(
        modifier = Modifier,
        topic = topic,
        selectTopic = {}
    )
}