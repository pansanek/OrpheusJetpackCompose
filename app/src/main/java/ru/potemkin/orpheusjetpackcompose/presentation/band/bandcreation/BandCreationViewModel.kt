package ru.potemkin.orpheusjetpackcompose.presentation.band.bandcreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.BandRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetMyUserBandsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import ru.potemkin.orpheusjetpackcompose.presentation.search.SearchScreenState
import javax.inject.Inject

class BandCreationViewModel @Inject constructor(
    private val getMyUserUseCase: GetMyUserUseCase,
    private val getMyUserBandsUseCase: GetMyUserBandsUseCase
) : ViewModel() {

    private val initialState = BandCreationScreenState.Initial

    private val _screenState = MutableLiveData<BandCreationScreenState>(initialState)
    val screenState: LiveData<BandCreationScreenState> = _screenState


    init {
        _screenState.value = BandCreationScreenState.Loading
        loadMyUserBands()
    }

    private fun loadMyUserBands() {
        viewModelScope.launch {
            _screenState.value = BandCreationScreenState.Bands(
                getMyUserBandsUseCase.invoke(getMyUserUseCase.invoke().id)
            )
        }
    }



}