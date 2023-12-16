package com.whereismymotivation.ui.login

import com.whereismymotivation.data.repository.LoginRepository
import com.whereismymotivation.data.repository.UserRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.message.Message
import com.whereismymotivation.ui.message.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.NavTarget
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.common.isValidEmail
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
    val navigator: Navigator,
    val messenger: Messenger
) : BaseViewModel(networkHelper, messenger) {

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
        if (_emailError.value.isNotEmpty()) _emailError.tryEmit("")
    }

    fun onPasswordChange(input: String) {
        _password.tryEmit(input)
        if (_passwordError.value.isNotEmpty()) _passwordError.tryEmit("")
    }

    fun basicLogin() {
        if (validate() && checkInternetConnectionWithMessage()) {
            launchNetwork {
                loginRepository.basicLogin(email.value, password.value)
                    .catch { handleNetworkError(it) }
                    .collect {
                        userRepository.saveCurrentUser(it)
                        navigator.navigateTo(NavTarget(Destination.Home.Feed, true))
                        messenger.deliver(Message.success("Login Success"))
                    }
            }
        }
    }

    private fun validate(): Boolean {
        var error = false
        if (!isValidEmail(email.value)) _emailError.tryEmit("Invalid Email").run { error = true }
        if (password.value.length < 6) _passwordError.tryEmit("Password length should be at least 6")
            .run { error = true }

        return !error
    }
}