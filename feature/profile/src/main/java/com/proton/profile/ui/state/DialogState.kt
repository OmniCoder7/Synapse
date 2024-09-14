package com.proton.profile.ui.state

import androidx.compose.ui.text.input.KeyboardType
import com.proton.profile.ui.composable.AddressType

sealed class DialogState {
    data object Hidden : DialogState()
    data class TextEdit(
        val type: ProfileItemType,
        val title: String,
        val initialValue: String,
        val onSave: (String) -> Unit,
        val keyboardType: KeyboardType
    ) : DialogState()

    data object AccountStatus : DialogState()
    data object DOB : DialogState()
    data object Gender : DialogState()
    data class AddressList(val type: AddressType) : DialogState()

    data object Security: DialogState()
    data object Privacy: DialogState()
    data object NotificationPreferences: DialogState()
    data object LanguageAndRegion: DialogState()
    data object Accessibility: DialogState()
    data object AccountManagement: DialogState()
}