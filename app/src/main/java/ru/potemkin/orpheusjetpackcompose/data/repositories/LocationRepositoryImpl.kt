package ru.potemkin.orpheusjetpackcompose.data.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import ru.potemkin.orpheusjetpackcompose.data.mappers.LocationMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.MessageMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import ru.potemkin.orpheusjetpackcompose.extentions.mergeWith
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(

) : LocationRepository {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)


    private val apiService = ApiFactory.appLocationApiService
    private val messageApiService = ApiFactory.appMessageApiService
    private val mapper = LocationMapper()
    private val messageMapper = MessageMapper()


    private val _locationItems = mutableListOf<LocationItem>()
    private val locationItems: List<LocationItem>
        get() = _locationItems.toList()

    private var nextFrom: String? = null
    private val refreshedListFlow = MutableSharedFlow<List<LocationItem>>()
    private val loadedListFlow = flow {
        // Проверяем, есть ли уже какие-то посты, и если есть, то их сначала отправляем
        if (locationItems.isNotEmpty()) {
            emit(locationItems)
        } else {
            // Если постов нет, добавляем моковые данные
            addMockData()
            emit(locationItems)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }
    private val locations: StateFlow<List<LocationItem>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = locationItems
        )
    override suspend fun addLocationItem(locationItem: LocationItem) {
        _locationItems.add(locationItem)
        refreshedListFlow.emit(locationItems)
    }



    override suspend fun deleteLocationItem(locationItem: LocationItem) {
        _locationItems.remove(locationItem)
        refreshedListFlow.emit(locationItems)
    }

    override suspend fun editLocationItem(locationItem: LocationItem) {
        val oldElement = getLocationItem(locationItem.id)
        _locationItems.remove(oldElement)
        addLocationItem(locationItem)
    }

    override fun getLocationItem(locationId: String): LocationItem {
        return _locationItems.find {
            it.id == locationId
        } ?: throw java.lang.RuntimeException("Element with id $locationId not found")
    }

    override fun getLocationsList(): StateFlow<List<LocationItem>> = locations




    suspend fun addMockData(){
        addLocationItem(LocationItem(
            id= "41",
            admin = UserItem(
                "110",
                "darkmagician",
                "Кирилл Денисов",
                "12341234",
                "10@gmail.com",
                "А что если...",
                UserType.ADMINISTRATOR,
                PhotoUrlItem(
                    "1910",
                    "https://live.staticflickr.com/5172/5484583701_e29c7aa381_b.jpg"
                ),
                PhotoUrlItem(
                    "11010",
                    "https://cdn.oboi7.com/225be3814aa2e64db6be175d8a197eeb26ea3d58/presmykayushijsya-pugalo-cherep-kid.jpg"
                ),
                UserSettingsItem(true, true)
            ),
            about = "Настоящий храм творчества и оплот музыкальной КУЛЬТуры, созданный музыкантами для музыкантов.",
            name = "КУЛЬТ",
            address = "Электрозаводская улица, 21, Москва",
            profilePicture = PhotoUrlItem("491","https://avatars.mds.yandex.net/i?id=150e8ad466d96a519c0372d21be120ebcd4beaef-5329555-images-thumbs&n=13"),
            latitude = 55.786505,
            longitude = 37.704143
        ))
        addLocationItem(LocationItem(
            id= "42",
            admin = UserItem(
                "111",
                "pavell",
                "Павел Литвинов",
                "12341234",
                "11@gmail.com",
                "Администратор лучшей репетиторской базы в Москве",
                UserType.ADMINISTRATOR,
                PhotoUrlItem(
                    "1911",
                    "https://i.pinimg.com/236x/1a/ec/84/1aec844e8866d63a65d4e9b705211d01.jpg?nii=t"
                ),
                PhotoUrlItem(
                    "1910",
                    "https://wallpapers.com/images/file/winding-road-dark-forest-shsyax3tzax42p9o.jpg"
                ),
                UserSettingsItem(true, true)
            ),
            about = "Репетиционная база",
            name = "Under the Ground",
            address = "ул. Правды, 24, стр. 3",
            profilePicture = PhotoUrlItem("492","https://bogatyr.club/uploads/posts/2023-03/1678283967_bogatyr-club-p-repetitsionnaya-studiya-foni-vkontakte-37.jpg"),
            latitude = 55.788085,
            longitude = 37.583625
        ))
    }

    companion object {

        private const val RETRY_TIMEOUT_MILLIS = 5L
    }
}