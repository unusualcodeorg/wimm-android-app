package com.whereismymotivation.ui.mybox

import androidx.compose.runtime.mutableStateListOf
import com.whereismymotivation.data.model.Content
import com.whereismymotivation.data.repository.ContentRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.browser.ContentBrowser
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MyBoxViewModel @Inject constructor(
    loader: Loader,
    navigator: Navigator,
    userRepository: UserRepository,
    private val messenger: Messenger,
    private val contentRepository: ContentRepository,
    private val contentBrowser: ContentBrowser
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "MyBoxViewModel"
    }

    private val _user = MutableStateFlow(runBlocking { userRepository.mustGetCurrentUser() })
    private val _contents = mutableStateListOf<Content>()

    val user = _user.asStateFlow()
    val contents: List<Content> = _contents

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
                        _contents.addAll(it)
                        currentPageNumber++
                        loading = false
                    }
                }
        }
    }

    fun loadMore() {
        loadMyBoxContent(currentPageNumber)
    }

    fun submit(content: Content) {
        if (content.submit == true) return

        launchNetwork {
            contentRepository.submitPrivateContent(content.id)
                .collect {
                    val index = _contents.indexOf(content)
                    if (index > -1)
                        _contents[index] = content.copy(submit = true)

                    messenger.deliver(Message.success(it))
                }
        }
    }

    fun unsubmit(content: Content) {
        if (content.submit != true) return

        launchNetwork {
            contentRepository.unsubmitPrivateContent(content.id)
                .collect {
                    val index = _contents.indexOf(content)
                    if (index > -1)
                        _contents[index] = content.copy(submit = false)

                    messenger.deliver(Message.success(it))
                }
        }
    }

    fun publish(content: Content) {
        if (content.private != true) return

        launchNetwork {
            contentRepository.publishGeneralContent(content.id)
                .collect {
                    val index = _contents.indexOf(content)
                    if (index > -1)
                        _contents[index] = content.copy(private = false)

                    messenger.deliver(Message.success(it))
                }
        }
    }

    fun unpublish(content: Content) {
        if (content.private == true) return

        launchNetwork {
            contentRepository.unpublishGeneralContent(content.id)
                .collect {
                    val index = _contents.indexOf(content)
                    if (index > -1)
                        _contents[index] = content.copy(private = true)

                    messenger.deliver(Message.success(it))
                }
        }
    }

    fun delete(content: Content) {
        launchNetwork {
            val call =
                if (_user.value.id == content.creator.id) contentRepository.removePrivateContent(
                    content.id
                )
                else contentRepository.removeContentBookmark(content.id)

            call.collect {
                _contents.remove(content)
                messenger.deliver(Message.success(it))
            }
        }
    }

    fun select(content: Content) {
        contentBrowser.show(content)
    }
}