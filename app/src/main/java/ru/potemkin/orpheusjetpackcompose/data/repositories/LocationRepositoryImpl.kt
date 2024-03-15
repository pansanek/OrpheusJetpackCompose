package ru.potemkin.orpheusjetpackcompose.data.repositories

import ru.potemkin.orpheusjetpackcompose.data.mappers.LocationMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.MessageMapper
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType
import ru.potemkin.orpheusjetpackcompose.domain.repositories.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(

) : LocationRepository {

    private val apiService = ApiFactory.appLocationApiService
    private val messageApiService = ApiFactory.appMessageApiService
    private val mapper = LocationMapper()
    private val messageMapper = MessageMapper()


    private val _locationItems = mutableListOf<LocationItem>()
    private val locationItems: List<LocationItem>
        get() = _locationItems.toList()

    private var nextFrom: String? = null
    init {
        addLocationItem(LocationItem(
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
        ))
        addLocationItem(LocationItem(
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
        ))
    }
    override fun addLocationItem(locationItem: LocationItem) {
        _locationItems.add(locationItem)
    }



    override fun deleteLocationItem(locationItem: LocationItem) {
        _locationItems.remove(locationItem)
    }

    override fun editLocationItem(locationItem: LocationItem) {
        val oldElement = getLocationItem(locationItem.id)
        _locationItems.remove(oldElement)
        addLocationItem(locationItem)
    }

    override fun getLocationItem(locationId: String): LocationItem {
        return _locationItems.find {
            it.id == locationId
        } ?: throw java.lang.RuntimeException("Element with id $locationId not found")
    }

    override fun getLocationsList(): List<LocationItem> {
        return _locationItems.toList()
    }

    override fun getMyUserLocation(userId: String): LocationItem {
        return _locationItems.find {
            it.admin.id == userId
        } ?: throw java.lang.RuntimeException("Element with id $userId not found")
    }
}