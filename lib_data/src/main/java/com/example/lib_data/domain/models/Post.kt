package com.example.lib_data.domain.models

/**
 *
 */
data class Post(
    val comments: MutableList<Comment> = mutableListOf(),
    val content: String,
    val createdAt: String,
    val id: Int,
    val username: String,
    val updatedAt: String
)
