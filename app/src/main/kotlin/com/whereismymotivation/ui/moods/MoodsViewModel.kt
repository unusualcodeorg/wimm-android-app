package com.whereismymotivation.ui.moods

import androidx.compose.runtime.mutableStateListOf
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
    val userRepository: UserRepository,
    val messenger: Messenger,
    private val moodRepository: MoodRepository
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "MoodsViewModel"
    }

    private val moods = mutableStateListOf<Mood>()

    private val _moodGraph = mutableStateListOf<MoodGraphData>()

    val moodGraph: List<MoodGraphData> = _moodGraph

    init {
        loadMoods()
    }

    private fun loadMoods() {
        viewModelScope.launch {
            val user = userRepository.mustGetCurrentUser()
            moodRepository.fetchMoods(user.id)
                .catch {
                    messenger.deliverRes(Message.error(R.string.something_went_wrong))
                }
                .collect { moodList ->
                    val graphDataList = moodList
                        .sortedBy { it.createdAt }
                        .map {
                            Pair(
                                CalendarUtils.getFormattedDate(it.createdAt) ?: "Unknown",
                                it.getValue()
                            )
                        }
                        .groupBy { it.first }
                        .map {
                            var sum = 0f
                            it.value.forEach { pair -> sum += pair.second }
                            MoodGraphData(
                                it.key,
                                sum / it.value.size,
                                it.value.toList().map { entry -> entry.second }
                            )
                        }

                    moods.addAll(moodList)
                    _moodGraph.addAll(graphDataList)
                }
        }
    }

    fun selectMood(code: Mood.Code) {
        viewModelScope.launch {
            val user = userRepository.mustGetCurrentUser()
            val now = CalendarUtils.now()
            val mood = Mood(0, String.Null(), code, user.id, now, now)
            val dateStr = CalendarUtils.getFormattedDate(now) ?: "Unknown"
            moodRepository
                .saveMood(mood)
                .catch {
                    messenger.deliverRes(Message.error(R.string.something_went_wrong))
                }
                .collect {
                    moods.add(mood.copy(id = it))
                    val graphData = _moodGraph.find { data -> data.x == dateStr }
                    if (graphData != null) {
                        val index = _moodGraph.indexOf(graphData)
                        if (index > -1) {
                            val values =
                                graphData.values.toMutableList()
                                    .apply { add(Mood.codeToValue(code)) }.toList()
                            _moodGraph[index] = graphData.copy(
                                values = values,
                                y = values.average().toFloat()
                            )
                        }
                    } else {
                        val newGraphData = MoodGraphData(
                            dateStr,
                            mood.getValue().toFloat(),
                            values = listOf(mood.getValue())
                        )
                        _moodGraph.add(newGraphData)
                    }
                    messenger.deliverRes(Message.success(R.string.mood_recorded_message))
                }

        }
    }

}