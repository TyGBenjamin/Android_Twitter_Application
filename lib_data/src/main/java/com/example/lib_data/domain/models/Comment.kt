package com.example.lib_data.domain.models

/**
 *
 */
data class Comment(
    val content: String,
    val createdAt: String,
    val updatedAt: String,
    val id: Int,
    val postId: Int,
    val username: String
)
