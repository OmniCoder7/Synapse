package com.proton.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.proton.data.util.TOKEN_DATASTORE

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN_DATASTORE)