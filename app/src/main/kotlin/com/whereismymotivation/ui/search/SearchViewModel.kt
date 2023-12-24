package com.whereismymotivation.ui.search

import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
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
}