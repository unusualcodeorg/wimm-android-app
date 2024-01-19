package com.whereismymotivation.ui.moods

import com.whereismymotivation.data.local.db.entity.Mood
import com.whereismymotivation.data.repository.AuthRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoodsViewModel @Inject constructor(
    loader: Loader,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    val navigator: Navigator,
    val messenger: Messenger
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "MoodsViewModel"
    }

    fun selectMood(code: Mood.Code) {}

}