package com.proton.profile.ui.state

data class ProfileItemData(
    val type: ProfileItemType,
    val iconId: Int,
    val title: String,
    val subTitle: String = "",
    val onEditSave: ((String) -> Unit)? = null,
    val onClickListener: (() -> Unit)? = null,
)