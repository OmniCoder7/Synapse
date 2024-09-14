package com.proton.data.util

import androidx.datastore.preferences.core.stringPreferencesKey

const val TOKEN_DATASTORE = "jwt_datastore"
val ACCESS_TOKEN = stringPreferencesKey("access_token")
val REFRESH_TOKEN = stringPreferencesKey("refresh_token")