package com.example.twittertest.presentation.screens.view_post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_data.data.repository.RepositoryImpl
import com.example.lib_data.domain.models.Comment
import com.example.lib_data.domain.models.Post
import com.example.lib_data.util.DataStorePrefSource
import com.example.lib_data.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 *
 */
@HiltViewModel
class ViewPostViewModel @Inject constructor(
    private val repo: RepositoryImpl,
    private val dataStore: DataStorePrefSource
) : ViewModel() {
    private val _post: MutableStateFlow<Resource<Post>> = MutableStateFlow(Resource.Loading)
    val post = _post.asStateFlow()
    private val _comments: MutableStateFlow<Resource<List<Comment>>> =
        MutableStateFlow(Resource.Loading)
    val comments = _comments.asStateFlow()

    /**
     *
     */
    fun postDetail(id: Int?) {
        println("Post Deatail with id $id")
        viewModelScope.launch {
            val token = dataStore.getPreferenceInfo().first()
            println("bout to ping the repo")
            _post.value = repo.getPostById("Bearer $token", id.toString())
            when (_post.value) {
                is Resource.Error -> println("we aint geting out")
                Resource.Loading -> println("yeah loop loop")
                is Resource.Success -> println("we got out ${(_post.value as Resource.Success<Post>).data}")
            }
        }
    }
}
