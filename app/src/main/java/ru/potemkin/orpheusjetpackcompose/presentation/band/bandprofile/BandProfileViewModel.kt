package ru.potemkin.orpheusjetpackcompose.presentation.band.bandprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.BandRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.CreatorType
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetBandPostsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetLocationPostsUseCase
import javax.inject.Inject

class BandProfileViewModel @Inject constructor(
    bandItem: BandItem,
    getBandPostsUseCase: GetBandPostsUseCase
) : ViewModel() {

    private val initialState = BandProfileScreenState.Initial

    private val _screenState = MutableLiveData<BandProfileScreenState>(initialState)
    val screenState: LiveData<BandProfileScreenState> = _screenState


    init {
        _screenState.value = BandProfileScreenState.Loading
        loadPosts(bandItem,getBandPostsUseCase)
    }

    private fun loadPosts(band: BandItem,getBandPostsUseCase: GetBandPostsUseCase) {
        viewModelScope.launch {
//            val feedPosts = repository.loadPosts(band.id)
            _screenState.value = BandProfileScreenState.Band(
                band = band,
                posts = getBandPostsUseCase.invoke(band.id)
            )
        }
    }





}