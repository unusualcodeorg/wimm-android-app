package com.whereismymotivation.ui.login

import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.common.isValidEmail
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    val navigator: Navigator,
) : BaseViewModel(networkHelper) {

    companion object {
        const val TAG = "LoginViewModel"
    }

    private val _email = MutableStateFlow("")
    private val _password = MutableStateFlow("")
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
        if (!isValidEmail(email.value)) _emailError.tryEmit("Invalid Email")
        if (password.value.length < 6) _passwordError.tryEmit("Password length should be at least 6")
    }
}