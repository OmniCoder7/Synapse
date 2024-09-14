package com.proton.profile.ui.state

sealed class ProfileItemType {
    data object Name : ProfileItemType()
    data object Username : ProfileItemType()
    data object PhoneNumber : ProfileItemType()
    data object DOB : ProfileItemType()
    data object EmailAddress : ProfileItemType()
    data object Gender : ProfileItemType()
    data object ShippingAddresses : ProfileItemType()
    data object BillingAddresses : ProfileItemType()
    data object AccountStatus : ProfileItemType()

    data object Security: ProfileItemType()
    data object Privacy: ProfileItemType()
    data object NotificationPreferences: ProfileItemType()
    data object LanguageAndRegion: ProfileItemType()
    data object Accessibility: ProfileItemType()
    data object AccountManagement: ProfileItemType()
}