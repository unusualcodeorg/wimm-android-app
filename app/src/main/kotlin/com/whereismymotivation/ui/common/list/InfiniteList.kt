package com.whereismymotivation.ui.common.list

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun InfiniteLazyColumn(
    modifier: Modifier = Modifier,
    loadMore: () -> Unit,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    extraItemsCount: Int = 0, // the number the extra items added in the list
    content: LazyListScope.() -> Unit
) {
    val isAtBottom by remember {
        derivedStateOf {
            val layoutInfo = state.layoutInfo
            if (layoutInfo.totalItemsCount == extraItemsCount) {
                // if no item in view
                false
            } else {
                val visibleItemsInfo = layoutInfo.visibleItemsInfo
                val lastVisibleItem = visibleItemsInfo.last()

                // if same item is as last called
                lastVisibleItem.index + 1 == layoutInfo.totalItemsCount
            }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { isAtBottom }
            .distinctUntilChanged()
            .collect {
                if (it) loadMore()
            }
    }

    LazyColumn(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        reverseLayout = reverseLayout,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        userScrollEnabled = userScrollEnabled,
        flingBehavior = flingBehavior,
        content = content
    )
}
