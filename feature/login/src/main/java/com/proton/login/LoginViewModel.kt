package com.proton.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proton.domain.error.NetworkError
import com.proton.domain.models.UIText
import com.proton.domain.useCase.LoginUseCase
import com.proton.domain.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    val loginError = MutableStateFlow<UIText?>(null)

    fun setEmail(email: String) {
        this.email.value = email
    }

    fun setPassword(password: String) {
        this.password.value = password
    }

    fun login(onSuccess: (Long) -> Unit) {
        viewModelScope.launch {
            when(val res = loginUseCase(email.value, password.value, viewModelScope)) {
                is Result.Error -> {
                    when(res.error) {
                        NetworkError.LoginError.UNAUTHORIZED -> loginError.value = UIText.StringResource(R.string.feature_login_login_error)
                        NetworkError.LoginError.CONNECTION -> loginError.value = UIText.StringResource(R.string.feature_login_connection_error)
                        NetworkError.LoginError.UNKNOWN -> loginError.value = UIText.StringResource(R.string.feature_login_unknown_error)
                    }
                }
                is Result.Success -> onSuccess.invoke(res.data.userId)
            }
        }
    }
}