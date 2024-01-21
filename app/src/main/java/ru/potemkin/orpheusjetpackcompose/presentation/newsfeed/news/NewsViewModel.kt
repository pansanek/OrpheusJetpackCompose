package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.mappers.PostMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.AddPostUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetPostListUseCase
import javax.inject.Inject

class NewsViewModel @Inject constructor(
//    private val getPostListUseCase: GetPostListUseCase,
//    private val addPostUseCase: AddPostUseCase
) : ViewModel() {
    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val repository = PostRepositoryImpl()

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadRecommendations()
    }

    private fun loadRecommendations() {
        viewModelScope.launch {
            val feedPosts = repository.getPostsList()
            _screenState.value = NewsFeedScreenState.Posts(posts = feedPosts)
        }
    }

    fun loadNextRecommendations() {
        _screenState.value = NewsFeedScreenState.Posts(
            posts = repository.postList,
            nextDataIsLoading = true
        )
        loadRecommendations()
    }

//    fun changeLikeStatus(feedPost: PostItem) {
//        viewModelScope.launch {
//            repository.changeLikeStatus(feedPost)
//            _screenState.value = NewsFeedScreenState.Posts(posts = repository.postList)
//        }
//    }

}