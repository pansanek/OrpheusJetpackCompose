package ru.potemkin.orpheusjetpackcompose.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.mappers.PostMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.UsersMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.states.CommentsViewState
import ru.potemkin.orpheusjetpackcompose.data.states.NewsScreenState
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.AddPostUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetPostListUseCase
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val getPostListUseCase: GetPostListUseCase,
    private val addPostUseCase: AddPostUseCase
) : ViewModel() {
    private val mapper = PostMapper()

    init {
        loadPostItems()
    }
    val postList = getPostListUseCase.getPostList()

    private val initialState = NewsScreenState.Posts(posts = postList)

    private val _screenState = MutableLiveData<NewsScreenState>(initialState)
    val screenState: LiveData<NewsScreenState> = _screenState

    private val _state = mutableStateOf(CommentsViewState())
    val state: State<CommentsViewState> = _state

    fun selectPost(post: PostItem) {
        Log.d("SELECTPOST", post.toString())
        _state.value = _state.value.copy(selectedPost = post)
    }

    fun clearSelectedPost() {
        _state.value = _state.value.copy(selectedPost = null)
    }

    fun openCommentDialog() {
        _state.value = _state.value.copy(isCommentDialogOpen = true)
    }

    fun closeCommentDialog() {
        _state.value = _state.value.copy(isCommentDialogOpen = false)
    }


    private fun loadPostItems() {
        viewModelScope.launch{
            val posts = ApiFactory.apiService.loadAllPosts()
            val postItems = mapper.mapPosts(posts)
            for(postItem in postItems){
                addPostUseCase.addPostItem(postItem)
            }
            _screenState.value = NewsScreenState.Posts(posts = postItems)
            Log.d("POSTS","LIST: ${postList.toString()}")
        }
    }
}