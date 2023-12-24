package com.whereismymotivation.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Article
import androidx.compose.material.icons.rounded.Audiotrack
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material.icons.rounded.Topic
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.model.UniversalSearchResult
import com.whereismymotivation.ui.common.image.NetworkImage
import com.whereismymotivation.ui.common.preview.SearchPreviewParameterProvider
import com.whereismymotivation.ui.theme.AppTheme

@Composable
fun Search(
    modifier: Modifier,
    searchViewModel: SearchViewModel,
    suggestionViewModel: SuggestionViewModel
) {
    val results = searchViewModel.results.collectAsStateWithLifecycle().value
    val query = searchViewModel.query.collectAsStateWithLifecycle().value
    SearchView(
        modifier = modifier,
        results = results,
        selectResult = { searchViewModel.selectResult(it) },
        search = { searchViewModel.search(it) },
        query = query
    ) {
        if (results.isEmpty()) {
            Suggestion(
                modifier = Modifier.padding(it),
                viewModel = suggestionViewModel
            )
        }
    }

}

@Composable
fun SearchView(
    modifier: Modifier,
    results: List<UniversalSearchResult>,
    selectResult: (UniversalSearchResult) -> Unit,
    search: (String) -> Unit,
    query: String,
    suggestion: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SearchAutoCompleter(
                search = search,
                query = query,
                elevated = true
            )
        },
        contentWindowInsets = WindowInsets(bottom = 0.dp)
    ) {
        suggestion(it)
        SearchResults(
            results = results,
            selectResult = selectResult
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAutoCompleter(
    modifier: Modifier = Modifier,
    search: (String) -> Unit,
    query: String,
    elevated: Boolean = false
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .shadow(if (elevated) 6.dp else 0.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            shape = RoundedCornerShape(32.dp),
            onValueChange = search,
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                }
            )
        )
    }
}

@Composable
private fun SearchResults(
    modifier: Modifier = Modifier,
    results: List<UniversalSearchResult>,
    selectResult: (UniversalSearchResult) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(results) {
            SearchResult(result = it, selectResult = selectResult)
        }
    }
}

@Composable
private fun SearchResult(
    modifier: Modifier = Modifier,
    result: UniversalSearchResult,
    selectResult: (UniversalSearchResult) -> Unit
) {
    Row(
        modifier = modifier
            .clickable(onClick = { selectResult(result) })
            .padding(vertical = 16.dp)
    ) {
        if (result.thumbnail != null)
            NetworkImage(
                url = result.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .size(112.dp, 64.dp)
                    .padding(start = 8.dp)
            )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = result.title,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            if (result.category == Content.Category.MENTOR_INFO)
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = result.extra,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
        }
        Icon(
            imageVector = when (result.category) {
                Content.Category.AUDIO -> Icons.Rounded.Audiotrack
                Content.Category.VIDEO -> Icons.Rounded.PlayCircleOutline
                Content.Category.IMAGE -> Icons.Rounded.Image
                Content.Category.YOUTUBE -> Icons.Rounded.PlayCircleOutline
                Content.Category.FACEBOOK_VIDEO -> Icons.Rounded.PlayCircleOutline
                Content.Category.ARTICLE -> Icons.Rounded.Article
                Content.Category.QUOTE -> Icons.Rounded.FormatQuote
                Content.Category.MENTOR_INFO -> Icons.Rounded.PersonOutline
                Content.Category.TOPIC_INFO -> Icons.Rounded.Topic
            },
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
    Divider()
}

@Preview(showBackground = true)
@Composable
private fun SearchPreview(
    @PreviewParameter(
        SearchPreviewParameterProvider::class,
        limit = 1
    ) result: UniversalSearchResult
) {
    AppTheme {
        SearchView(
            modifier = Modifier,
            results = listOf(
                result.copy(),
                result.copy(),
                result.copy(),
                result.copy(),
                result.copy(),
                result.copy(),
            ),
            selectResult = {},
            search = {},
            query = "query"
        ) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchAutoCompleterPreview() {
    AppTheme {
        SearchAutoCompleter(search = {}, query = "query")
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchResultsPreview(
    @PreviewParameter(
        SearchPreviewParameterProvider::class,
        limit = 1
    ) result: UniversalSearchResult
) {
    AppTheme {
        SearchResults(
            results = listOf(
                result.copy(),
                result.copy(),
                result.copy(),
                result.copy(),
                result.copy(),
                result.copy(),
            ),
            selectResult = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchResultPreview(
    @PreviewParameter(
        SearchPreviewParameterProvider::class,
        limit = 1
    ) result: UniversalSearchResult
) {
    AppTheme {
        SearchResult(result = result, selectResult = {})
    }
}