package com.proton.register

import ConfirmPasswordState
import PasswordState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proton.domain.error.NetworkError
import com.proton.domain.models.UIText
import com.proton.domain.models.User
import com.proton.domain.useCase.RegisterUseCase
import com.proton.domain.util.Result
import com.proton.register.state.EmailState
import com.proton.register.state.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterState())
    val uiState: StateFlow<RegisterState> get() = _uiState

    private val _email = MutableStateFlow(EmailState())
    val email: StateFlow<EmailState> get() = _email

    private val _password = MutableStateFlow(PasswordState())
    val password: StateFlow<PasswordState> get() = _password

    private val _registerError: MutableStateFlow<UIText?> = MutableStateFlow(null)
    val registerError: StateFlow<UIText?> get() = _registerError

    private val _confirmPassword = MutableStateFlow(ConfirmPasswordState(_password.value))
    val confirmPassword: StateFlow<ConfirmPasswordState> get() = _confirmPassword

    fun setFirstName(firstName: String) {
        _uiState.value = _uiState.value.copy(firstName = firstName)
    }

    fun setLastName(lastName: String) {
        _uiState.value = _uiState.value.copy(lastName = lastName)
    }

    fun register(onSuccess: (Long) -> Unit) {
        viewModelScope.launch {
            val user = User(
                firstName = _uiState.value.firstName,
                lastName = _uiState.value.lastName,
                userName = _uiState.value.userName,
                email = _email.value.text,
                gender = _uiState.value.gender,
                dob = convertMillisToDate(_uiState.value.dob)
            )
            when (val res = registerUseCase(
                user = User(
                    firstName = "viskin",
                    lastName = "saka",
                    email = "viskinSaka@gmail.com"
                ), coroutineScope = viewModelScope, password = "#100Viskin"
            )) {
                is Result.Error -> when (res.error) {
                    NetworkError.RegisterError.UNKNOWN_ERROR -> _registerError.value =
                        UIText.StringResource(R.string.unknown_error)

                    NetworkError.RegisterError.BAD_REQUEST -> _registerError.value =
                        UIText.StringResource(R.string.bad_request)

                    NetworkError.RegisterError.CONNECTION -> _registerError.value =
                        UIText.StringResource(R.string.connection_error)

                    NetworkError.RegisterError.CONFLICT -> _registerError.value =
                        UIText.StringResource(R.string.conflict_error)
                }

                is Result.Success -> onSuccess.invoke(res.data.userId)
            }
        }
    }

    fun onGenderChange(gender: String) {
        _uiState.update { it.copy(gender = gender) }
    }

    fun setDob(dob: Long?) {
        _uiState.update { it.copy(dob = dob ?: 0) }
    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    fun setUsername(username: String) {
        _uiState.update { it.copy(userName = username) }
    }
}