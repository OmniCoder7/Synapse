package com.proton.profile.ui.composable

import androidx.compose.runtime.Composable
import kotlin.reflect.KFunction0

@Composable
fun AddressListDialog(addressType: AddressType, onDismiss: () -> Unit) {
    
}

enum class AddressType {
    Shipping,
    Billing
}