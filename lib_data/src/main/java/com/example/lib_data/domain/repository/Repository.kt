package com.example.lib_data.domain.repository

import com.example.lib_data.domain.models.Comment
import com.example.lib_data.domain.models.Post
import com.example.lib_data.domain.models.PostAdd
import com.example.lib_data.domain.models.Token
import com.example.lib_data.util.Resource

/**
 *
 */
interface Repository {
    /**
     *
     */
    suspend fun loginUser(userName: String, password: String): Resource<Token>

    /**
     *
     */
    suspend fun createAndSaveUser(name: String, userName: String, password: String): Resource<Any>

    /**
     *
     */
    suspend fun getPosts(token: String): Resource<List<Post>>

    /**
     *
     */
    suspend fun getPostById(token: String?, id: String?): Resource<Post>

    /**
     *
     */
    suspend fun addPost(token: String, post: PostAdd): Resource<Post>

    /**
     *
     */
    suspend fun createComment(token: String, comment: Comment): Resource<Comment>


}
