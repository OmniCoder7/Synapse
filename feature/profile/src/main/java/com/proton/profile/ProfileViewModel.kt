package com.proton.profile

import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proton.domain.models.User
import com.proton.domain.useCase.GetUserUseCase
import com.proton.profile.ui.composable.AddressType
import com.proton.profile.ui.state.DialogState
import com.proton.profile.ui.state.ProfileItemData
import com.proton.profile.ui.state.ProfileItemType
import com.proton.profile.ui.state.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserUseCase: GetUserUseCase,
    id: Long,
) : ViewModel() {

    private val _user = MutableStateFlow(User())
    private val user: StateFlow<User> = _user.asStateFlow()

    val userState: StateFlow<ProfileUiState> = _user
        .map { ProfileUiState.Success(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ProfileUiState.Loading
        )

    val userInfo: StateFlow<List<ProfileItemData>> = _user
        .map { createUserInfoList(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val accountInfo: StateFlow<List<ProfileItemData>> = _user
        .map { createAccountInfoList(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        loadUser(id)
    }

    private val _dialogState = MutableStateFlow<DialogState>(DialogState.Hidden)
    val dialogState: StateFlow<DialogState> = _dialogState.asStateFlow()

    fun showDialog(item: ProfileItemData) {
        _dialogState.value = when (item.type) {
            ProfileItemType.Name -> DialogState.TextEdit(
                type = item.type,
                title = "Name",
                initialValue = "${user.value.firstName} ${user.value.lastName}",
                onSave = { _user.update { user: User -> user.copy(firstName = it) } },
                keyboardType = KeyboardType.Text
            )
            ProfileItemType.Username -> DialogState.TextEdit(
                type = item.type,
                title = "Username",
                initialValue = user.value.userName,
                onSave = { _user.update { user: User -> user.copy(userName = it) } },
                keyboardType = KeyboardType.Text
            )
            ProfileItemType.EmailAddress -> DialogState.TextEdit(
                type = item.type,
                title = "Email Address",
                initialValue = user.value.email,
                onSave = { _user.update { user: User -> user.copy(email = it) } },
                keyboardType = KeyboardType.Email
            )
            ProfileItemType.PhoneNumber -> DialogState.TextEdit(
                type = item.type,
                title = "Phone Number",
                initialValue = user.value.number.toString(),
                onSave = { _user.update { user: User -> user.copy(number = it.toLong()) } },
                keyboardType = KeyboardType.Phone
            )

            ProfileItemType.AccountStatus -> DialogState.AccountStatus
            ProfileItemType.DOB -> DialogState.DOB
            ProfileItemType.Gender -> DialogState.Gender
            ProfileItemType.BillingAddresses -> DialogState.AddressList(AddressType.Billing)
            ProfileItemType.ShippingAddresses -> DialogState.AddressList(AddressType.Shipping)
            ProfileItemType.Accessibility -> DialogState.Accessibility
            ProfileItemType.AccountManagement -> DialogState.AccountManagement
            ProfileItemType.LanguageAndRegion -> DialogState.LanguageAndRegion
            ProfileItemType.NotificationPreferences -> DialogState.NotificationPreferences
            ProfileItemType.Privacy -> DialogState.Privacy
            ProfileItemType.Security -> DialogState.Security
        }
    }

    fun hideDialog() {
        _dialogState.value = DialogState.Hidden
    }

    private fun loadUser(id: Long) {
        viewModelScope.launch {
            _user.value = getUserUseCase(id, viewModelScope).await()
        }
    }

    fun updateUserField(update: (User) -> User) {
        _user.update(update)
    }

    private fun createUserInfoList(user: User): List<ProfileItemData> = listOf(
        ProfileItemData(
            type = ProfileItemType.Name,
            iconId = R.drawable.name,
            title = "Name",
            subTitle = "${user.firstName} ${user.lastName}",
            onEditSave = { updateUserField {  user: User-> user.copy(firstName = it) } }
        ),
        ProfileItemData(
            type = ProfileItemType.Username,
            iconId = R.drawable.username,
            title = "Username",
            subTitle = user.userName,
            onEditSave = { updateUserField {  user: User-> user.copy(userName = it) } }
        ),
        ProfileItemData(
            type = ProfileItemType.Gender,
            iconId = R.drawable.gender,
            title = "Gender",
            subTitle = user.gender,
            onEditSave = { updateUserField {  user: User-> user.copy(gender = it) } }
        ),
        ProfileItemData(
            type = ProfileItemType.PhoneNumber,
            iconId = R.drawable.phone_number,
            title = "Phone number",
            subTitle = user.number.toString(),
            onEditSave = { updateUserField { user: User -> user.copy(number = it.toLong()) } }
        ),
        ProfileItemData(
            type = ProfileItemType.DOB,
            iconId = R.drawable.date_of_birth,
            title = "Date of birth",
            subTitle = user.dob,
            onEditSave = { updateUserField { user: User -> user.copy(dob = it) } }
        ),
        ProfileItemData(
            type = ProfileItemType.EmailAddress,
            iconId = R.drawable.account,
            title = "Account Detail",
            subTitle = user.email,
            onEditSave = { updateUserField { user: User -> user.copy(email = it) } }
        )
    )

    private fun createAccountInfoList(user: User) =
        listOf(
            ProfileItemData(
                type = ProfileItemType.Security,
                iconId = R.drawable.security,
                title = "Security",
                onEditSave = { updateUserField {  user: User-> user.copy(firstName = it) } }
            ),
            ProfileItemData(
                type = ProfileItemType.Privacy,
                iconId = R.drawable.privacy,
                title = "Privacy",
                onEditSave = { updateUserField {  user: User-> user.copy(userName = it) } }
            ),
            ProfileItemData(
                type = ProfileItemType.NotificationPreferences,
                iconId = R.drawable.notification,
                title = "Notification Preferences",
                onEditSave = { updateUserField { user: User -> user.copy(number = it.toLong()) } }
            ),
            ProfileItemData(
                type = ProfileItemType.LanguageAndRegion,
                iconId = R.drawable.language,
                title = "Language and Region",
                onEditSave = { updateUserField { user: User -> user.copy(dob = it) } }
            ),
            ProfileItemData(
                type = ProfileItemType.AccountStatus,
                iconId = R.drawable.account_status,
                title = "Account Status",
                onEditSave = { updateUserField { user: User -> user.copy(email = it) } }
            ),
            ProfileItemData(
                type = ProfileItemType.AccountManagement,
                iconId = R.drawable.account_management,
                title = "Account Management",
                onEditSave = { updateUserField { user: User -> user.copy(email = it) } }
            )
        )

}