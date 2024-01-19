package com.whereismymotivation.ui.moods

import androidx.lifecycle.viewModelScope
import com.whereismymotivation.R
import com.whereismymotivation.data.local.db.entity.Mood
import com.whereismymotivation.data.repository.MoodRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.common.CalendarUtils
import com.whereismymotivation.utils.common.Null
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoodsViewModel @Inject constructor(
    loader: Loader,
    navigator: Navigator,
    userRepository: UserRepository,
    val messenger: Messenger,
    private val moodRepository: MoodRepository
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "MoodsViewModel"
    }

    val user = userRepository.getCurrentUser()!!

    private fun loadMoods(){

    }

    fun selectMood(code: Mood.Code) {
        viewModelScope.launch {
            val now = CalendarUtils.now()
            moodRepository
                .saveMood(Mood(0, String.Null(), code, user.id, now, now))
                .catch {
                    messenger.deliverRes(Message.error(R.string.something_went_wrong))
                }
                .collect {
                    messenger.deliverRes(Message.success(R.string.mood_recorded_message))
                }

        }
    }

}