package com.android.mitcontaskmanagement.ui.auth.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mitcontaskmanagement.data.repo.AuthRepo
import com.android.mitcontaskmanagement.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepo: AuthRepo) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginState = MutableStateFlow<Resource<Unit>>(Resource.Empty())
    val loginState: StateFlow<Resource<Unit>> = _loginState

    fun onEmailTextChange(email: String) {
        _email.value = email
    }

    fun onPasswordTextChange(password: String) {
        _password.value = password
    }

    private fun verifyUserInput(): Boolean {
        //val regex =  "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}\$"
        //return _email.value.matches(Regex(regex)) && _password.value.isNotEmpty()
        return _email.value.isNotEmpty()
    }

    fun onLoginButtonPressed() = viewModelScope.launch {
        if (verifyUserInput()) {
            _loginState.emit(Resource.Loading())
            _loginState.emit(authRepo.loginUser(_email.value))
        } else
            _loginState.emit(Resource.Error("Enter details correctly"))
    }

}
