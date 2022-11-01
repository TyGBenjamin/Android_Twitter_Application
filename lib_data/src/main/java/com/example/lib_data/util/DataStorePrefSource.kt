package com.example.lib_data.util

import kotlinx.coroutines.flow.Flow

/**
 */
interface DataStorePrefSource {
    /**
     *
     */
    fun getPreferenceInfo(): Flow<String?>

    /**
     *
     */
    suspend fun setPreferenceInfo(token: String)

    /**
     *
     */
    fun getUser(): Flow<String>

    /**
     *
     */
    suspend fun setUser(username: String)
}

