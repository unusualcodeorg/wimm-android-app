package com.whereismymotivation.ui.home

import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    messenger: Messenger
) : BaseViewModel(networkHelper, messenger) {

    companion object {
        const val TAG = "HomeViewModel"
    }

}