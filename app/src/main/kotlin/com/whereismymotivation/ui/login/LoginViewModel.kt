package com.whereismymotivation.ui.login

import com.whereismymotivation.data.repository.AuthRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Message
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.NavTarget
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.common.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    loader: Loader,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    val navigator: Navigator,
    val messenger: Messenger,
) : BaseViewModel(loader, messenger) {

    companion object {
        const val TAG = "LoginViewModel"
    }

    private val _email = MutableStateFlow("admin@janisharali.com")
    private val _password = MutableStateFlow("changeit")
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

    fun basicLogin() {
        if (validate()) {
            launchNetwork {
                authRepository.basicLogin(email.value, password.value)
                    .collect {
                        userRepository.saveCurrentAuth(it)
                        navigator.navigateTo(NavTarget(Destination.Home.Feed.route, true))
                        messenger.deliver(Message.success("Login Success"))
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