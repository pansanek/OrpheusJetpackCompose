package ru.potemkin.orpheusjetpackcompose.presentation.band.bandcreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.BandRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.presentation.search.SearchScreenState
import javax.inject.Inject

class BandCreationViewModel @Inject constructor() : ViewModel() {

    private val initialState = BandCreationScreenState.Initial

    private val _screenState = MutableLiveData<BandCreationScreenState>(initialState)
    val screenState: LiveData<BandCreationScreenState> = _screenState

    private val repository = BandRepositoryImpl()
    private val userRepository = UserRepositoryImpl()
    init {
        _screenState.value = BandCreationScreenState.Loading
        loadMyUserBands()
    }

    private fun loadMyUserBands() {
        viewModelScope.launch {
            val bands = repository.loadBands()
            val myUser = userRepository.getMyUser()
            val myUserBands = mutableListOf<BandItem>()
            for (band: BandItem in bands){
                if (myUser in band.members)
                    myUserBands.add(band)
            }
            _screenState.value = BandCreationScreenState.Bands(myUserBands)
        }
    }





//    fun changeLikeStatus(feedPost: PostItem) {
//        viewModelScope.launch {
//            repository.changeLikeStatus(feedPost)
//            _screenState.value = NewsFeedScreenState.Posts(posts = repository.postList)
//        }
//    }

}