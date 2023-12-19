package com.whereismymotivation.ui.common.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember

@Composable
fun rememberScrollLastItemVisible(state: LazyStaggeredGridState): State<Boolean> {
    val indexCache = remember { mutableIntStateOf(0) }
    return remember {
        derivedStateOf {
            val layoutInfo = state.layoutInfo
            if (layoutInfo.totalItemsCount == 0) {
                // if no item in view
                false
            } else {
                val visibleItemsInfo = layoutInfo.visibleItemsInfo
                val lastVisibleItem = visibleItemsInfo.last()

                // if same item is as last called
                if (lastVisibleItem.index == indexCache.intValue) {
                    false
                } else {
                    val isLast = lastVisibleItem.index + 1 == layoutInfo.totalItemsCount
                    if (isLast) {
                        indexCache.intValue = lastVisibleItem.index
                        true
                    } else {
                        false
                    }
                }
            }
        }
    }
}

@Composable
fun rememberScrollLastItemVisible(state: LazyListState): State<Boolean> {
    val indexCache = remember { mutableIntStateOf(0) }
    return remember {
        derivedStateOf {
            val layoutInfo = state.layoutInfo
            if (layoutInfo.totalItemsCount == 0) {
                // if no item in view
                false
            } else {
                val visibleItemsInfo = layoutInfo.visibleItemsInfo
                val lastVisibleItem = visibleItemsInfo.last()

                // if same item is as last called
                if (lastVisibleItem.index == indexCache.intValue) {
                    false
                } else {
                    val isLast = lastVisibleItem.index + 1 == layoutInfo.totalItemsCount
                    if (isLast) {
                        indexCache.intValue = lastVisibleItem.index
                        true
                    } else {
                        false
                    }
                }
            }
        }
    }
}
