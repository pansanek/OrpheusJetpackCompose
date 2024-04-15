package ru.potemkin.orpheusjetpackcompose.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
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
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.UserProfileScreenState
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getMusiciansListUseCase: GetMusiciansListUseCase,
    private val getBandListUseCase: GetBandListUseCase
) : ViewModel() {




    val musicianListFlow = getMusiciansListUseCase.invoke()

    val bandListFlow = getBandListUseCase.invoke()

    val screenState = musicianListFlow
        .combine(bandListFlow) { musicians, bands ->
            SearchScreenState.Finds(bands = bands, musicians = musicians)
        }
        .filter { it.musicians.isNotEmpty() } // Фильтруем, чтобы убедиться, что у нас есть посты
        .map { it as SearchScreenState } // Преобразуем к типу SearchScreenState
        .onStart { emit(SearchScreenState.Loading) }





}