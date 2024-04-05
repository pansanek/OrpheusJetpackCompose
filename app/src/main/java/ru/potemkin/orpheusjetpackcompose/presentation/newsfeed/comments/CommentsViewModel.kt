package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetCommentsUseCase
import javax.inject.Inject


class CommentsViewModel @Inject constructor(
    postItem: PostItem,
    private val getCommentsUseCase: GetCommentsUseCase

) : ViewModel() {

    private val repository = PostRepositoryImpl()

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    init {
        loadComments(postItem)
    }

    private fun loadComments(postItem: PostItem) {
        viewModelScope.launch {
            val comments = getCommentsUseCase.invoke(postItem.id)
            _screenState.value = CommentsScreenState.Comments(
                comments = comments
            )
        }
    }
}