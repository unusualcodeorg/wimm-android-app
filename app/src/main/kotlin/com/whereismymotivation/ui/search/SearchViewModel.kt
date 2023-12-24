package com.whereismymotivation.ui.search

import com.whereismymotivation.data.model.UniversalSearchResult
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    loader: Loader,
    messenger: Messenger
) : BaseViewModel(networkHelper, loader, messenger) {

    companion object {
        const val TAG = "SearchViewModel"
    }

    val _results = MutableStateFlow<List<UniversalSearchResult>>(emptyList())
    val _query = MutableStateFlow("")

    val results = _results.asStateFlow()
    val query = _query.asStateFlow()

    fun search(query: String) {
        _query.value = query

    }

    fun selectResult(result: UniversalSearchResult) {}

}