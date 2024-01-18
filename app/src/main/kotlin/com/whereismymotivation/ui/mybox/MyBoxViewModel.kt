package com.whereismymotivation.ui.mybox

import androidx.compose.runtime.mutableStateListOf
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.repository.ContentRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyBoxViewModel @Inject constructor(
    loader: Loader,
    messenger: Messenger,
    navigator: Navigator,
    private val contentRepository: ContentRepository
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "MyBoxViewModel"
    }

    val contents = mutableStateListOf<Content>()

    private val pageItemCount = 20
    private var currentPageNumber = 1
    private var loading = false

    init {
        loadMyBoxContent(currentPageNumber)
    }

    private fun loadMyBoxContent(pageNumber: Int) {
        if (loading) return
        loading = true
        launchNetwork(error = { loading = false }) {
            contentRepository
                .fetchMyBoxContents(pageNumber, pageItemCount)
                .collect {
                    if (it.isNotEmpty()) {
                        contents.addAll(it)
                        currentPageNumber++
                        loading = false
                    }
                }
        }
    }

    fun loadMore() {
        loadMyBoxContent(currentPageNumber)
    }

    fun delete(content: Content) {

    }
}