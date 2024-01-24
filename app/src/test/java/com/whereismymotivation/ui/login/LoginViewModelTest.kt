package com.whereismymotivation.ui.login

import com.whereismymotivation.data.model.Auth
import com.whereismymotivation.data.repository.AuthRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
class LoginViewModelTest {

    private val loader: Loader = mockk(relaxed = true)

    private val navigator: Navigator = mockk(relaxed = true)

    private val messenger: Messenger = mockk(relaxed = true)

    private val authRepository: AuthRepository = mockk()

    private val userRepository: UserRepository = mockk()

    private val viewModel: LoginViewModel = LoginViewModel(
        loader, authRepository, userRepository, navigator, messenger
    )

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    @Throws(java.lang.Exception::class)
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should succeed login with credentials`() = runTest {
        // Data
        val auth: Auth = mockk()

        val email = "a@b.com"
        val password = "password"

        viewModel.onEmailChange(email)
        viewModel.onPasswordChange(password)

        // Arrange
        coEvery { authRepository.basicLogin(any(), any()) } returns flow { emit(auth) }

        every { userRepository.saveCurrentAuth(any()) } just runs

        every { userRepository.getFirebaseToken() } returns "token"

        every { userRepository.userExists() } returns true

        coEvery { userRepository.sendFirebaseToken(any()) } returns flow { emit("") }

        every { userRepository.setFirebaseTokenSent() } just runs

        every { userRepository.isOnBoardingComplete() } returns true

        // Act
        viewModel.basicLogin()

        // Assert
        coVerify { authRepository.basicLogin(email, password) }
        verify { messenger.deliver(Message.success("Login Success")) }
        verify { navigator.navigateTo(Destination.Home.route, true) }
    }
}