package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
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


    val commentsFlow = getCommentsUseCase.invoke(postItem)

    val screenState = commentsFlow
        .filter { it.isNotEmpty() }
        .map { CommentsScreenState.Comments(comments = it) as CommentsScreenState }
        .onStart { emit(CommentsScreenState.Loading) }


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
        }
    }



    private fun getNewCommentId(postItem: PostItem): String {
        var commentList = getCommentsUseCase.invoke(postItem)
        val indexes: MutableList<String> = ArrayList()
        var largest: Int = 0
        for (i in commentList.value) {
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