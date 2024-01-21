package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem


class CommentsViewModel(
    feedPost: PostItem,
    application: Application
) : ViewModel() {

    private val repository = PostRepositoryImpl()

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(feedPost)
    }

    private fun loadComments(feedPost: PostItem) {
        viewModelScope.launch {
            val comments = feedPost.comments
            _screenState.value = CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = comments
            )
        }
    }
}