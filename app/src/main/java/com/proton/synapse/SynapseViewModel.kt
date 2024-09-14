package com.proton.synapse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proton.domain.models.User
import com.proton.domain.service.AuthService
import com.proton.domain.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SynapseViewModel(
    authService: AuthService,
) : ViewModel() {

    val authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Loading)

    init {
        viewModelScope.launch {
            authState.update {
                try {
                    when (val res = authService.authenticate(coroutineScope = viewModelScope)) {
                        is Result.Error -> AuthState.Unauthenticated
                        is Result.Success -> AuthState.Authenticated(res.data)
                    }
                } catch (e: NullPointerException) {
                    AuthState.Unauthenticated
                }
            }
        }
    }
}

sealed class AuthState {
    data class Authenticated(val user: User) : AuthState()
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
}