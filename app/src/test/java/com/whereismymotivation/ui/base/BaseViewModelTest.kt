package com.whereismymotivation.ui.base

import android.net.Uri
import com.whereismymotivation.data.remote.response.ApiErrorResponse
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.log.Logger
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class BaseViewModelTest {

    class TestConcreteViewModel(loader: Loader, messenger: Messenger, navigator: Navigator) :
        BaseViewModel(loader, messenger, navigator) {
        fun testLaunchNetwork(
            silent: Boolean = false,
            error: (ApiErrorResponse) -> Unit = {},
            block: suspend CoroutineScope.() -> Unit
        ) {
            launchNetwork(silent, error, block)
        }
    }

    private val loader: Loader = mockk()

    private val navigator: Navigator = mockk()

    private val messenger: Messenger = mockk()

    private val viewModel = spyk(TestConcreteViewModel(loader, messenger, navigator))

    private val testDispatcher = UnconfinedTestDispatcher()

    private val route: String = "route"

    private val uri: Uri = mockk()

    private val exception = Exception("Network Error")

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkObject(Logger)

        every { loader.start() } just Runs
        every { loader.stop() } just Runs
        every { messenger.deliver(any()) } just Runs
        every { messenger.deliverRes(any()) } just Runs
        every { navigator.navigateTo(route, any()) } just Runs
        every { navigator.navigateTo(uri, any()) } just Runs
        every { Logger.d(any(), exception) } just Runs
        every { Logger.record(exception) } just Runs
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `launchNetwork should start and stop loader`() = runTest {

        // Act
        viewModel.testLaunchNetwork {}

        // Assert
        verify { loader.start() }
        verify { loader.stop() }
    }

    @Test
    fun `launchNetwork should deliver network error`() = runTest {

        // Data
        val apiErrorResponse = ApiErrorResponse()
        val errorHandler: (ApiErrorResponse) -> Unit = mockk()
        every { errorHandler(apiErrorResponse) } just Runs

        // Act
        viewModel.testLaunchNetwork(error = errorHandler) { throw exception }

        // Assert
        verify { errorHandler(apiErrorResponse) }
        verify { loader.start() }
        verify { loader.stop() }
    }

    @Test
    fun `launchNetwork should not show loader in silent mode`() = runTest {

        // Data
        val apiErrorResponse = ApiErrorResponse()
        val errorHandler: (ApiErrorResponse) -> Unit = mockk()
        every { errorHandler(apiErrorResponse) } just Runs

        // Act
        viewModel.testLaunchNetwork(silent = true, error = errorHandler) { throw exception }

        // Assert
        verify { errorHandler(apiErrorResponse) }
        verify(inverse = true) { loader.start() }
        verify(inverse = true) { loader.stop() }
    }
}