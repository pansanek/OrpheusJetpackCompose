package ru.potemkin.orpheusjetpackcompose.presentation.map.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.potemkin.orpheusjetpackcompose.data.repositories.LocationRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.StatisticType
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import javax.inject.Inject

class MapViewModel @Inject constructor() : ViewModel() {


    private val initialState = MapScreenState.Initial

    private val _screenState = MutableLiveData<MapScreenState>(initialState)
    val screenState: LiveData<MapScreenState> = _screenState

    private val repository = LocationRepositoryImpl()

    init {
        _screenState.value = MapScreenState.Loading
        loadLocations()
    }

    private fun loadLocations() {
        viewModelScope.launch {
//            val locations = repository.loadLocations()
            val locations = addMockData()
            _screenState.value = MapScreenState.Locations(locations = locations)
        }
    }

    fun addMockData():List<LocationItem>{
        val mockData = mutableListOf<LocationItem>(
            LocationItem(
                id= "a9d28f2a-5eae-48bf-85f7-7c8dde3ec22c",
                admin = UserItem(
                    "51bdc118-e76b-4372-8678-6822658cefed",
                    "pansanek",
                    "Sasha",
                    "12341234",
                    "email@gmail.com",
                    "Hehe",
                    UserType.MUSICIAN,
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    UserSettingsItem(true,true)
                ),
                about = "Repbase",
                name = "KVLT",
                address = "УЛИЦА 2",
                profilePicture = PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fc5325235234","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
            )
        )
        return mockData
    }
}