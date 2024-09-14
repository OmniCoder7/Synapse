package com.proton.network.repositories

interface TokenManager {
    suspend fun getAccessToken(): String?
    suspend fun saveAccessToken(token: String)
    suspend fun deleteAccessToken()
    suspend fun getRefreshToken(): String?
    suspend fun saveRefreshToken(token: String)
    suspend fun deleteRefreshToken()
}