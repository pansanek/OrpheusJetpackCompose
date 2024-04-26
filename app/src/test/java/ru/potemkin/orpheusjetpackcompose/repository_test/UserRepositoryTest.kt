package ru.potemkin.orpheusjetpackcompose.repository_test

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.potemkin.orpheusjetpackcompose.data.repositories.UserRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType

class UserRepositoryTest {

    private lateinit var userRepository: UserRepositoryImpl

    @Before
    fun setUp() {
        userRepository = UserRepositoryImpl()
    }

    @Test
    fun `test addUserItem`() = runBlocking {
        // Given
        val userItem = UserItem(
            "11",
            "pansanek",
            "Саша Потемкин",
            "12341234",
            "1@gmail.com",
            "Просто барабанщик, гитарист, басист и так далее.",
            UserType.MUSICIAN,
            PhotoUrlItem(
                "191",
                "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
            ),
            PhotoUrlItem(
                "1101",
                "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
            ),
            UserSettingsItem(true, true)
        )

        // When
        userRepository.addUserItem(userItem)

        // Then
        val users = userRepository.getLocalUserList()
        assertEquals(4, users.size)
        assertEquals(userItem, users.first())
    }

    @Test
    fun `test addMusicianItem`() = runBlocking {
        // Given
        val musicianItem = MusicianItem(
            "81",
            UserItem(
                "11",
                "pansanek",
                "Саша Потемкин",
                "12341234",
                "1@gmail.com",
                "Просто барабанщик, гитарист, басист и так далее.",
                UserType.MUSICIAN,
                PhotoUrlItem(
                    "111",
                    "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
                ),
                PhotoUrlItem(
                    "112",
                    "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                ),
                UserSettingsItem(true, true)
            ),
            "Металкор",
            "Барабаны"
        )

        // When
        userRepository.addMusicianItem(musicianItem)

        // Then
        val musicians = userRepository.getLocalMusicianList()
        assertEquals(1, musicians.size)
    }

    @Test
    fun `test getMyUser`() {
        // When
        val myUser = userRepository.getMyUser()

        // Then
        assertEquals("11", myUser.id)
    }

    @Test
    fun `test setMyUser`() = runBlocking{
        // Given
        val userId = "11"
        val userItem = UserItem(
            "11",
            "pansanek",
            "Саша Потемкин",
            "12341234",
            "1@gmail.com",
            "Просто барабанщик, гитарист, басист и так далее.",
            UserType.MUSICIAN,
            PhotoUrlItem(
                "191",
                "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
            ),
            PhotoUrlItem(
                "1101",
                "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
            ),
            UserSettingsItem(true, true)
        )
        userRepository.addUserItem(userItem)
        // When
        userRepository.setMyUser(userId)

        // Then
        val myUser = userRepository.getMyUser()
        assertEquals(userId, myUser.id)
        assertEquals(userItem.login, myUser.login)
        assertEquals(userItem.name, myUser.name)
        // Проверка остальных полей опущена для краткости
    }

    @Test
    fun `test deleteUserItem`() = runBlocking {
        // Given
        val userItem = UserItem(
            "11",
            "pansanek",
            "Саша Потемкин",
            "12341234",
            "1@gmail.com",
            "Просто барабанщик, гитарист, басист и так далее.",
            UserType.MUSICIAN,
            PhotoUrlItem(
                "191",
                "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
            ),
            PhotoUrlItem(
                "1101",
                "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
            ),
            UserSettingsItem(true, true)
        )
        userRepository.addUserItem(userItem)

        // When
        userRepository.deleteUserItem(userItem)

        // Then
        val users = userRepository.getLocalUserList()
        assertEquals(7, users.size)
    }

    @Test
    fun `test editUserItem`() = runBlocking {
        // Given
        val userItem = UserItem(
            "11",
            "pansanek",
            "Саша Потемкин",
            "12341234",
            "1@gmail.com",
            "Просто барабанщик, гитарист, басист и так далее.",
            UserType.MUSICIAN,
            PhotoUrlItem(
                "191",
                "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
            ),
            PhotoUrlItem(
                "1101",
                "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
            ),
            UserSettingsItem(true, true)
        )
        userRepository.addUserItem(userItem)
        // When
        userRepository.editUserItem(userItem)

        // Then
        val myUser = userRepository.getMyUser()
        assertEquals(userItem.id, myUser.id)
    }

    @Test
    fun `test editMusicianItem`() = runBlocking {
        // Given
        val originalMusician = MusicianItem(
            "81",
            UserItem(
                "11",
                "pansanek",
                "Саша Потемкин",
                "12341234",
                "1@gmail.com",
                "Просто барабанщик, гитарист, басист и так далее.",
                UserType.MUSICIAN,
                PhotoUrlItem(
                    "111",
                    "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
                ),
                PhotoUrlItem(
                    "112",
                    "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                ),
                UserSettingsItem(true, true)
            ),
            "Металкор",
            "Барабаны"
        )
        val updatedMusician = MusicianItem(
            "81",
            UserItem(
                "11",
                "pansanek",
                "Саша Потемкин",
                "12341234",
                "1@gmail.com",
                "Просто барабанщик",
                UserType.MUSICIAN,
                PhotoUrlItem(
                    "111",
                    "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a712c853c116199&type=album"
                ),
                PhotoUrlItem(
                    "112",
                    "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
                ),
                UserSettingsItem(true, true)
            ),
            "Металкор",
            "Барабаны"
        )
        userRepository.addMusicianItem(originalMusician)

        // When
        userRepository.editMusicianItem(updatedMusician)

        // Then
        val musicians = userRepository.getLocalMusicianList()
        assertEquals(updatedMusician, musicians.filter { it.id == originalMusician.id }.first())
    }
}
