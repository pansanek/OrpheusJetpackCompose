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
                    UserType.ADMINISTRATOR,
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://img-fotki.yandex.ru/get/5803/12042645.1d/0_965fd_fcd89bb9_orig.jpg"),
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    UserSettingsItem(true,true)
                ),
                about = "Настоящий храм творчества и оплот музыкальной КУЛЬТуры, созданный музыкантами для музыкантов.",
                name = "КУЛЬТ",
                address = "Электрозаводская улица, 21, Москва",
                profilePicture = PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fc5325235234","https://avatars.mds.yandex.net/i?id=150e8ad466d96a519c0372d21be120ebcd4beaef-5329555-images-thumbs&n=13"),
                latitude = 55.786505,
                longitude = 37.704143
            ),
            LocationItem(
                id= "a9d28f2a-5eae-48bf-85f7-7c8dde3ec22d",
                admin = UserItem(
                    "51bdc118-e76b-4372-8678-6822658cefed",
                    "pansanek",
                    "Sasha",
                    "12341234",
                    "email@gmail.com",
                    "Hehe",
                    UserType.ADMINISTRATOR,
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de3","https://gl.weburg.net/00/galleries/925/big/123397.jpg"),
                    PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fca1b784de4","https://metalplanetmusic.com/wp-content/uploads/2020/10/120098107_4476121869095823_416408964908687768_n.jpg"),
                    UserSettingsItem(true,true)
                ),
                about = "Repbase",
                name = "Under the Ground",
                address = "ул. Правды, 24, стр. 3",
                profilePicture = PhotoUrlItem("b59ae42e-8859-441a-9a3a-2fc5325235234","https://bogatyr.club/uploads/posts/2023-03/1678283967_bogatyr-club-p-repetitsionnaya-studiya-foni-vkontakte-37.jpg"),
                latitude = 55.788085,
                longitude = 37.583625
            )

        )
        return mockData
    }
}