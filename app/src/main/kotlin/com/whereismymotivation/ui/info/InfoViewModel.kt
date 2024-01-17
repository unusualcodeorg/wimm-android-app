package com.whereismymotivation.ui.info

import com.whereismymotivation.R
import com.whereismymotivation.data.repository.AuthRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    loader: Loader,
    private val messenger: Messenger,
    private val navigator: Navigator,
    private val authRepository: AuthRepository
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "InfoViewModel"
    }

    private var trying = false

    fun retry() {
        if (trying) return
        trying = true
        launchNetwork(
            silent = true,
            error = {
                trying = false
                messenger.deliverRes(Message.error(R.string.server_connection_error))
            }) {
            authRepository
                .serverHeartbeat()
                .collect {
                    trying = false
                    navigator.navigateBack()
                }
        }
    }
}