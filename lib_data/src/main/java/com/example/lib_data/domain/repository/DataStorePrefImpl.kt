package com.example.lib_data.domain.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.lib_data.util.DataStorePrefSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//Preference Name
const val PREFERENCE_NAME = "MyDataStore"

//Instance of DataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

/**
 *
 */
class DataStorePrefImpl @Inject constructor(
    private val context: Context
) : DataStorePrefSource {
    override fun getPreferenceInfo(): Flow<String?> {
        return context.dataStore.data.map { pref ->
            pref[stringPreferencesKey("token")] ?: "No Token Found"
        }
    }

    override suspend fun setPreferenceInfo(token: String) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey("token")] = token
        }
    }

    override fun getUser(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey("username")] ?: ""
        }
    }

    override suspend fun setUser(username: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey("username")] = username
        }
    }
}
