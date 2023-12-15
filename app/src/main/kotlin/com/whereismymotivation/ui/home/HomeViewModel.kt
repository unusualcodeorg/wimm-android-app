package com.whereismymotivation.ui.home

import androidx.lifecycle.viewModelScope
import com.whereismymotivation.data.repository.MentorRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.utils.common.Resource
import com.whereismymotivation.utils.log.Logger
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    private val mentorRepository: MentorRepository
) : BaseViewModel(networkHelper) {

    companion object {
        const val TAG = "HomeViewModel"
    }

    init {
        viewModelScope.launch {
            mentorRepository.fetchMentorDetails("65782defe0004d39fa1fe864")
                .catch {
                    handleNetworkError(it)
                    Logger.d(TAG, "fetchMentorDetails error : $it")
                }
                .collect {
                    _message.value = Resource.success(it.toString())
                    Logger.d(TAG, "fetchMentorDetails success : $it")
                }
        }
    }

}