package ru.potemkin.orpheusjetpackcompose.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.BandRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetBandListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMusiciansListUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetMyUserUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.user_usecases.GetUserListUseCase
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getMusiciansListUseCase: GetMusiciansListUseCase,
    private val getBandListUseCase: GetBandListUseCase
) : ViewModel() {

    private val initialState = SearchScreenState.Initial

    private val _screenState = MutableLiveData<SearchScreenState>(initialState)
    val screenState: LiveData<SearchScreenState> = _screenState


    init {
        _screenState.value = SearchScreenState.Loading
        loadBandsAndUsers()
    }

    private fun loadBandsAndUsers() {
        viewModelScope.launch {
            _screenState.value = SearchScreenState.Finds(
                musicians = getMusiciansListUseCase.invoke(),
                bands = getBandListUseCase.invoke()
            )
        }
    }





}