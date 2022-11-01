package com.example.lib_data.data.repository

import android.accounts.NetworkErrorException
import com.example.lib_data.data.remote.ApiService
import com.example.lib_data.domain.models.Comment
import com.example.lib_data.domain.models.Post
import com.example.lib_data.domain.models.PostAdd
import com.example.lib_data.domain.models.Token
import com.example.lib_data.domain.models.UserObject
import com.example.lib_data.domain.repository.Repository
import com.example.lib_data.util.Resource
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *
 */
class RepositoryImpl @Inject constructor(
    private var apiInstance: ApiService,
//    dataStore: DataStorePrefSource
) : Repository {
    override suspend fun loginUser(userName: String, password: String): Resource<Token> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val res = apiInstance.loginUser(userName, password)
                if (res.isSuccessful && res.body() != null) {
                    println(res.body())
                    Resource.Success(res.body()!!)


                } else {
                    Resource.Error("Someting Wrong")
                }
            } catch (e: NetworkErrorException) {
                Resource.Error(e.message.toString())
            }
        }

    override suspend fun createAndSaveUser(
        name: String,
        userName: String,
        password: String
    ): Resource<Any> = withContext(Dispatchers.IO) {
        return@withContext try {
            val res = apiInstance.saveUser(UserObject(name, userName, password))
            if (res.isSuccessful && res.body() != null) {
                println(res.body())
                Resource.Success(res.body()!!)


            } else {
                Resource.Error("Someting Wrong")
            }
        } catch (e: NetworkErrorException) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun getPosts(token: String): Resource<List<Post>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val res = apiInstance.getPost(token)
                if (res.isSuccessful && res.body() != null) {
                    println("token HERE $token")
                    println("Res.Body ${res.body()}")
                    Resource.Success(res.body()!!)
                } else {
                    Resource.Error("Error with retrieving posts")
                }
            } catch (e: java.lang.Exception) {
                Resource.Error(e.message.toString())
            }
        }


    override suspend fun getPostById(token: String?, id: String?): Resource<Post> =
        withContext(Dispatchers.IO) {
            println("bout to hit the api")
            return@withContext try {
                val res = apiInstance.getPostById(token!!, id)
                if (res.isSuccessful && res.body() != null) {
                    println("token HERE $id")
                    println("Res.Body ${res.body()}")
                    Resource.Success(res.body()!!)
                } else {
                    Resource.Error("Error with retrieving posts")
                }
            } catch (e: java.lang.Exception) {
                Resource.Error(e.message.toString())
            }
        }

    override suspend fun addPost(token: String, post: PostAdd): Resource<Post> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val res = apiInstance.addPost(token, post)
                if (res.isSuccessful && res.body() != null) {
                    Resource.Success(res.body()!!)
                } else {
                    Resource.Error("I AM BROKEN MAAN!!")
                }
            } catch (e: java.lang.Exception) {
                Resource.Error(e.message.toString())
            }
        }

    override suspend fun createComment(token: String, comment: Comment): Resource<Comment> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val res = apiInstance.createComment(token, comment)
                if (res.isSuccessful && res.body() != null) {
                    Resource.Success(res.body()!!)
                } else {
                    Resource.Error("NICE IT'S BROKEN")
                }
            } catch (e: java.lang.Exception) {
                Resource.Error(e.message.toString())
            }
        }
}
