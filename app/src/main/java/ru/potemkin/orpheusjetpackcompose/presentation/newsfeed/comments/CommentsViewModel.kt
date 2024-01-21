package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments

//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
//import ru.potemkin.orpheusjetpackcompose.data.states.CommentsScreenState
//import ru.potemkin.orpheusjetpackcompose.domain.entities.PostComment
//import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
//import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetCommentsUseCase
//import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetPostListUseCase
//
//class CommentsViewModel(
//) : ViewModel() {
//
//
//    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
//    val screenState: LiveData<CommentsScreenState> = _screenState
//    init {
//        loadComments()
//    }
//
//    fun loadComments() {
//        val comments = mutableListOf<PostComment>().apply {
//            repeat(10) {
//                add(PostComment(id = it,
//                    userId = it,
//                    username = "Test",
//                    text= "Test",
//                    timestamp = 125235
//                    ))
//            }
//        }
//        _screenState.value = CommentsScreenState.Comments(
//            feedPost = feedPost,
//            comments = comments
//        )
//    }
//}