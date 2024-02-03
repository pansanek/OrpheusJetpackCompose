package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import javax.inject.Inject


class CommentsViewModel @Inject constructor(
    feedPost: PostItem,
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