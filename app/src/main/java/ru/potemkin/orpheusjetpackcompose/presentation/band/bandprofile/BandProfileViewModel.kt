package ru.potemkin.orpheusjetpackcompose.presentation.band.bandprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.EditBandUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.EditPostUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetBandPostsUseCase
import javax.inject.Inject

class BandProfileViewModel @Inject constructor(
    bandItem: BandItem,
    getBandPostsUseCase: GetBandPostsUseCase,
    private val editBandUseCase: EditBandUseCase,
    private val editPostUseCase: EditPostUseCase,
) : ViewModel() {

    private val initialState = BandProfileScreenState.Initial

    private val _screenState = MutableLiveData<BandProfileScreenState>(initialState)
    val screenState: LiveData<BandProfileScreenState> = _screenState

    val postList = getBandPostsUseCase.invoke(bandItem.id)
    init {
        _screenState.value = BandProfileScreenState.Loading
        loadPosts(bandItem)
    }

    private fun loadPosts(band: BandItem) {
        viewModelScope.launch {
//            val feedPosts = repository.loadPosts(band.id)
            _screenState.value = BandProfileScreenState.Band(
                band = band,
                posts = postList
            )
        }
    }

    fun changeBandProfile(
        oldProfile: BandItem,
        bandName: String,
        genre: String,
        profilePictureUrl: String
    ) {
        val newBand = BandItem(
            id = oldProfile.id,
            name = bandName,
            members = oldProfile.members,
            genre = genre,
            photo = PhotoUrlItem(
                id = oldProfile.id,
                url = profilePictureUrl
            ),
        )
        editBandUseCase.invoke(newBand)
        changeBandPosts(bandName, profilePictureUrl)

    }

    private fun changeBandPosts(userName: String, profilePictureUrl: String) {
        for (i in postList) {
            i.creatorName = userName
            i.creatorPicture.url = profilePictureUrl
            editPostUseCase.invoke(i)
        }

    }


}