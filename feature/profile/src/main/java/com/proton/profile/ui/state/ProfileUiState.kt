package com.proton.profile.ui.state

import com.proton.domain.models.User

sealed class ProfileUiState {
    data object Loading : ProfileUiState()
    data class Success(val user: User) : ProfileUiState()
}
