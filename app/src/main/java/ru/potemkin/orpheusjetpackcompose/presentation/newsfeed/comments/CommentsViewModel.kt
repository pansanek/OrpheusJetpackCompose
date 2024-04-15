package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.AddCommentUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetCommentsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject


class CommentsViewModel @Inject constructor(
    postItem: PostItem,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val getMyUserUseCase: GetMyUserUseCase

) : ViewModel() {


    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        Log.d("ViewModel", "Exception caught by exception handler")
    }

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    val comments = getCommentsUseCase.invoke(postItem.id)
    val commentsFlow = MutableStateFlow(comments)
    init {
        loadComments(postItem)
    }

    private fun loadComments(postItem: PostItem) {
        viewModelScope.launch {
            _screenState.value = CommentsScreenState.Comments(
                comments = commentsFlow.value
            )
        }
    }
    fun createComment(content:String, postItem: PostItem){
        viewModelScope.launch(exceptionHandler) {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
            val currentDate = sdf.format(Date())
            addCommentUseCase.invoke(
                CommentItem(
                    id = getNewCommentId(postItem),
                    post_id = postItem.id,
                    user = getMyUser(),
                    text = content,
                    timestamp = currentDate
                )
            )
            commentsFlow.value = getCommentsUseCase.invoke(postItem.id)
        }
    }

    private fun getNewCommentId(postItem: PostItem): String {
        var notificationList = getCommentsUseCase.invoke(postItem.id)
        val indexes: MutableList<String> = ArrayList()
        var largest: Int = 0
        for (i in notificationList) {
            indexes.add(i.id)
        }
        for (i in indexes) {
            if (largest < i.toIntOrNull()!!)
                largest = i.toIntOrNull()!!
        }
        largest = largest + 1
        return largest.toString()
    }
    fun getMyUser(): UserItem {
        return getMyUserUseCase.invoke()
    }
}