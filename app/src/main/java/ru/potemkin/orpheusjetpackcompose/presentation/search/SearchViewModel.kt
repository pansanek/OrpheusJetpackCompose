package ru.potemkin.orpheusjetpackcompose.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.BandRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.MusicianRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.PostRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import javax.inject.Inject

class SearchViewModel @Inject constructor() : ViewModel() {

    private val initialState = SearchScreenState.Initial

    private val _screenState = MutableLiveData<SearchScreenState>(initialState)
    val screenState: LiveData<SearchScreenState> = _screenState

    private val bandRepository = BandRepositoryImpl()
    private val musicianRepository = MusicianRepositoryImpl()
    init {
        _screenState.value = SearchScreenState.Loading
        loadBandsAndUsers()
    }

    private fun loadBandsAndUsers() {
        viewModelScope.launch {
            val bands = bandRepository.loadBands()
            val musicians = musicianRepository.loadMusicians()
            _screenState.value = SearchScreenState.Finds(bands, musicians)
        }
    }





//    fun changeLikeStatus(feedPost: PostItem) {
//        viewModelScope.launch {
//            repository.changeLikeStatus(feedPost)
//            _screenState.value = NewsFeedScreenState.Posts(posts = repository.postList)
//        }
//    }

}