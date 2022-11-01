package com.example.lib_data.domain.models

import com.google.gson.annotations.SerializedName

/**
 *
 */
data class Token(
    @SerializedName(value = "access_token")
    val accessToken: String,
    @SerializedName(value = "refresh_token")
    val refreshToken: String
)
