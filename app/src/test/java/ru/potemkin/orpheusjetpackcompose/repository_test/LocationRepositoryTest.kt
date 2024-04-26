package ru.potemkin.orpheusjetpackcompose.repository_test

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.potemkin.orpheusjetpackcompose.data.repositories.LocationRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType

class LocationRepositoryTest {

    private lateinit var locationRepository: LocationRepositoryImpl

    @Before
    fun setUp() {
        locationRepository = LocationRepositoryImpl()
    }

    @Test
    fun `test addLocationItem`() = runBlocking {
        // Given
        val locationItem = LocationItem(
            id= "1",
            admin = UserItem(
                "110",
                "darkmagician",
                "Кирилл Денисов",
                "1231234",
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
            longitude = 37.70143
        )

        // When
        locationRepository.addLocationItem(locationItem)

        // Then
        val locations = locationRepository.getLocalLocationList()
        assertEquals(1, locations.size)
    }

    @Test
    fun `test deleteLocationItem`() = runBlocking {
        // Given
        val locationItem = LocationItem(
            id= "1",
            admin = UserItem(
                "110",
                "darkmagician",
                "Кирилл Денисов",
                "1231234",
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
            longitude = 37.70143
        )
        locationRepository.addLocationItem(locationItem)

        // When
        locationRepository.deleteLocationItem(locationItem)

        // Then
        val locations = locationRepository.getLocalLocationList()
        assertEquals(2, locations.size)
    }

    @Test
    fun `test editLocationItem`() = runBlocking {
        // Given
        val originalLocation = LocationItem(
            id= "1",
            admin = UserItem(
                "110",
                "darkmagician",
                "Кирилл Денисов",
                "1231234",
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
            longitude = 37.70143
        )
        val updatedLocation = LocationItem(
            id= "1",
            admin = UserItem(
                "110",
                "darkmagician",
                "Кирилл Денисов",
                "1231234",
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
            name = "КУЛЬТТ",
            address = "Электрозаводская улица, 21, Москва",
            profilePicture = PhotoUrlItem("491","https://avatars.mds.yandex.net/i?id=150e8ad466d96a519c0372d21be120ebcd4beaef-5329555-images-thumbs&n=13"),
            latitude = 55.786505,
            longitude = 37.70143
        )
        locationRepository.addLocationItem(originalLocation)

        // When
        locationRepository.editLocationItem(updatedLocation)

        // Then
        val locations = locationRepository.getLocalLocationList()
        assertEquals(updatedLocation, locationRepository.getLocationItem("1"))
    }

    @Test(expected = RuntimeException::class)
    fun `test getLocationItem when location does not exist`() {
        // When
        locationRepository.getLocationItem("1")
    }

    @Test
    fun `test getLocationItem when location exists`() = runBlocking {
        // Given
        val locationItem = LocationItem(
            id= "1",
            admin = UserItem(
                "110",
                "darkmagician",
                "Кирилл Денисов",
                "1231234",
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
            longitude = 37.70143
        )
        locationRepository.addLocationItem(locationItem)

        // When
        val retrievedLocation = locationRepository.getLocationItem("1")

        // Then
        assertEquals(locationItem, retrievedLocation)
    }
}
