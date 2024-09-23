package com.proton.synapse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proton.domain.error.NetworkError
import com.proton.domain.models.User
import com.proton.domain.service.AuthService
import com.proton.domain.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SynapseViewModel(
    authService: AuthService,
) : ViewModel() {
    private val _authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState
        .onStart {
            _authState.update {
                when (val res = authService.authenticate(viewModelScope)) {
                    is Result.Error -> when (res.error) {
                        NetworkError.AuthError.UNAUTHORIZED -> AuthState.Unauthenticated
                        NetworkError.AuthError.UNKNOWN -> AuthState.Unauthenticated
                        NetworkError.AuthError.TOO_MANY_REQUESTS -> AuthState.Unauthenticated
                    }

                    is Result.Success -> AuthState.Authenticated(res.data)
                }
            }
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AuthState.Loading
        )
}

sealed class AuthState {
    data class Authenticated(val user: User) : AuthState()
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
}