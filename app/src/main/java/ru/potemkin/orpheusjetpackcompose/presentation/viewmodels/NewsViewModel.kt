package ru.potemkin.orpheusjetpackcompose.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.states.CommentsViewState
import ru.potemkin.orpheusjetpackcompose.data.states.MapViewState
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetPostListUseCase

class NewsViewModel : ViewModel() {

    private val repository = PostRepositoryImpl

    private val getPostListUseCase = GetPostListUseCase(repository)

    val postList = getPostListUseCase.getPostList()

    private val _state = mutableStateOf(CommentsViewState())
    val state: State<CommentsViewState> = _state

    fun selectPost(post:PostItem) {
        Log.d("SELECTPOST",post.toString())
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
}