package ru.potemkin.orpheusjetpackcompose.presentation.band.bandprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.BandRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.MusicianRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.UserProfileScreenState
import javax.inject.Inject

class BandProfileViewModel @Inject constructor(bandItem: BandItem) : ViewModel() {

    private val initialState = BandProfileScreenState.Initial

    private val _screenState = MutableLiveData<BandProfileScreenState>(initialState)
    val screenState: LiveData<BandProfileScreenState> = _screenState

    private val repository = BandRepositoryImpl()
    init {
        _screenState.value = BandProfileScreenState.Loading
        loadPosts(bandItem)
    }

    private fun loadPosts(band: BandItem) {
        viewModelScope.launch {
            val feedPosts = repository.loadPosts(band.id)
            _screenState.value = BandProfileScreenState.Band(band = band,posts = feedPosts)
        }
    }





//    fun changeLikeStatus(feedPost: PostItem) {
//        viewModelScope.launch {
//            repository.changeLikeStatus(feedPost)
//            _screenState.value = NewsFeedScreenState.Posts(posts = repository.postList)
//        }
//    }

}