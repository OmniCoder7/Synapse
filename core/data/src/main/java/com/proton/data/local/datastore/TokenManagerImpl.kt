package com.proton.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.proton.data.util.ACCESS_TOKEN
import com.proton.data.util.REFRESH_TOKEN
import com.proton.network.repositories.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TokenManagerImpl(
    private val datastore: DataStore<Preferences>,
): TokenManager {

    override suspend fun getAccessToken(): String? =
        datastore.data.map {
            it[ACCESS_TOKEN]
        }.first()

    override suspend  fun saveAccessToken(token: String) {
        datastore.edit {
            it[ACCESS_TOKEN] = token
        }
    }

    override suspend fun deleteAccessToken() {
        datastore.edit {
            it.remove(ACCESS_TOKEN)
        }
    }

    override suspend fun getRefreshToken(): String? =
        datastore.data.map {
            it[REFRESH_TOKEN]
        }.first()

    override suspend fun saveRefreshToken(token: String) {
        datastore.edit {
            it[REFRESH_TOKEN] = token
        }
    }

    override suspend fun deleteRefreshToken() {
        datastore.edit {
            it.remove(REFRESH_TOKEN)
        }
    }
}