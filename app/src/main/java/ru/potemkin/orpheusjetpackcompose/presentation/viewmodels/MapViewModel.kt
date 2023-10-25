package ru.potemkin.orpheusjetpackcompose.presentation.viewmodels

import androidx.lifecycle.ViewModel
import ru.potemkin.orpheusjetpackcompose.data.LocationRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.GetLocationListUseCase

class MapViewModel : ViewModel() {

    private val repository = LocationRepositoryImpl

    private val getLocationListUseCase = GetLocationListUseCase(repository)

    val locationList = getLocationListUseCase.getLocationList()

}