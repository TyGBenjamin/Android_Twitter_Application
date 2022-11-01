package com.example.lib_data.data.remote

import com.example.lib_data.domain.models.Comment
import com.example.lib_data.domain.models.Post
import com.example.lib_data.domain.models.PostAdd
import com.example.lib_data.domain.models.Token
import com.example.lib_data.domain.models.UserObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *
 */
interface ApiService {

    // Refresh Token
    @GET("refresh/token")
    suspend fun refreshToken(): Response<Token>

    @POST("users/save")
    suspend fun saveUser(
        @Body name: UserObject
    ): Response<Any>

    // LOGIN USER
    @POST("login")
    suspend fun loginUser(
        @Query("username") userName: String,
        @Query("password") password: String
    ): Response<Token>


    // Post & Comment Routes
    @GET("posts")
    suspend fun getPost(@Header("Authorization") token: String): Response<List<Post>>

    @POST("post")
    suspend fun addPost(@Header("Authorization") token: String, @Body post: PostAdd): Response<Post>


    @GET("posts/{id}")
    suspend fun getPostById(
        @Header("Authorization") token: String,
        @Path("id") id: String?
    ): Response<Post>


    @POST("post/comment")
    suspend fun createComment(
        @Header("Authorization") token: String,
        @Body comment: Comment
    ): Response<Comment>

}
