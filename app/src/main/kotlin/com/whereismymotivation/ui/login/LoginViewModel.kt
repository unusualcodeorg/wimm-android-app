package com.whereismymotivation.ui.login

import com.whereismymotivation.BuildConfig
import com.whereismymotivation.data.repository.AuthRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.common.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    loader: Loader,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    val navigator: Navigator,
    val messenger: Messenger,
) : BaseViewModel(loader, messenger, navigator) {

    companion object {
        const val TAG = "LoginViewModel"
    }

    private val _email = MutableStateFlow(if (BuildConfig.DEBUG) "admin@janisharali.com" else "")
    private val _password = MutableStateFlow(if (BuildConfig.DEBUG) "changeit" else "")
    private val _emailError = MutableStateFlow("")
    private val _passwordError = MutableStateFlow("")

    val email = _email.asStateFlow()
    val password = _password.asStateFlow()
    val emailError = _emailError.asStateFlow()
    val passwordError = _passwordError.asStateFlow()

    fun onEmailChange(input: String) {
        _email.tryEmit(input)
        if (emailError.value.isNotEmpty()) _emailError.tryEmit("")
    }

    fun onPasswordChange(input: String) {
        _password.tryEmit(input)
        if (passwordError.value.isNotEmpty()) _passwordError.tryEmit("")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun basicLogin() {
        if (validate()) {
            launchNetwork {
                authRepository.basicLogin(email.value, password.value)
                    .flatMapLatest { auth ->
                        flow {
                            userRepository.saveCurrentAuth(auth)
                            val firebaseToken = userRepository.getFirebaseToken()
                            if (firebaseToken != null && userRepository.userExists()) {
                                userRepository.sendFirebaseToken(firebaseToken)
                                    .catch {
                                        emit(auth)
                                    }
                                    .collect {
                                        userRepository.setFirebaseTokenSent()
                                        emit(auth)
                                    }
                            } else {
                                emit(auth)
                            }
                        }
                    }
                    .collect {
                        messenger.deliver(Message.success("Login Success"))
                        if (userRepository.isOnBoardingComplete()) {
                            navigator.navigateTo(Destination.Home.route, true)
                        } else {
                            navigator.navigateTo(Destination.Onboarding.route, true)
                        }
                    }
            }
        }
    }

    private fun validate(): Boolean {
        var error = false
        if (!email.value.isValidEmail()) _emailError.tryEmit("Invalid Email").run { error = true }
        if (password.value.length < 6) _passwordError.tryEmit("Password length should be at least 6")
            .run { error = true }
        return !error
    }
}