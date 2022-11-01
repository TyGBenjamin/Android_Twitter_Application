package com.example.twittertest.presentation.screens.addComment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_data.data.repository.RepositoryImpl
import com.example.lib_data.domain.models.Comment
import com.example.lib_data.util.DataStorePrefSource
import com.example.lib_data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 *
 *
 */
@HiltViewModel
class CommentViewModel @Inject constructor(
    private val repo: RepositoryImpl,
    private val datastore: DataStorePrefSource
) : ViewModel() {
    private val _createPost: MutableStateFlow<Resource<Comment>> =
        MutableStateFlow(Resource.Loading)


    /**
     *
     */
    fun createComment(comment: Comment) {
        viewModelScope.launch {

            val token = datastore.getPreferenceInfo().first()

            _createPost.value = repo.createComment("Bearer $token", comment)
            when (_createPost.value) {

                is Resource.Error -> println("ERROR!!!")
                Resource.Loading -> println("Loading..")
                is Resource.Success -> {
                    println("got em ${_createPost.value}")
//                    val user = post.username
//                    getUser(user)
                }
                null -> {}
            }
        }
    }
}
