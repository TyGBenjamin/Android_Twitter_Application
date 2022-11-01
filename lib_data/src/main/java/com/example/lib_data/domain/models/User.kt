package com.example.lib_data.domain.models

/**
 *
 */
data class User(
    val id: String,
    val name: String,
    val userName: String,
    val password: String,
    val posts: List<Post>,
    val roles: String
)
