package ru.potemkin.orpheusjetpackcompose.repository_test

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.potemkin.orpheusjetpackcompose.data.repositories.BandRepositoryImpl
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType

class BandRepositoryTest {

    private lateinit var bandRepository: BandRepositoryImpl

    @Before
    fun setUp() {
        bandRepository = BandRepositoryImpl()
    }

    @Test
    fun `test addBandItem`() = runBlocking {
        // Given
        val bandItem =  BandItem(
            "1",
            "Bad Omens",
            listOf(
                UserItem(
                    "12",
                    "noahbadomens",
                    "Ной Себастьян",
                    "12341234",
                    "email@gmail.com",
                    "Вокалист",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "192",
                        "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
                    ),
                    PhotoUrlItem(
                        "1102",
                        "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                UserItem(
                    "13",
                    "nicholasriffruff",
                    "Николас Руффило",
                    "12341234",
                    "3@gmail.com",
                    "Гитарист группы Bad Omens",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "193",
                        "https://i.pinimg.com/originals/ed/bc/29/edbc295d751aa4ab31850fd052b7c37a.jpg"
                    ),
                    PhotoUrlItem(
                        "1103",
                        "https://images.squarespace-cdn.com/content/v1/54a06130e4b0510339b40c5e/1527895061651-7SU9556569GML2IQW2OI/BO-5.jpg"
                    ),
                    UserSettingsItem(true, true)
                )
            ),
            "Металкор",
            PhotoUrlItem(
                "791",
                "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
            )
        )

        // When
        bandRepository.addBandItem(bandItem)

        // Then
        val bands = bandRepository.getLocalBandsList()
        assertEquals(1, bands.size)
        assertEquals(bandItem, bands.first())
    }

    @Test
    fun `test addBandMemberItem`() = runBlocking {
        // Given
        val bandItem =  BandItem(
            "1",
            "Bad Omens",
            listOf(

            ),
            "Металкор",
            PhotoUrlItem(
                "791",
                "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
            )
        )
        val userItem =  UserItem(
            "11",
            "pansanek",
            "Саша Потемкин",
            "12341234",
            "1@gmail.com",
            "Просто барабанщик, гитарист, басист и так далее.",
            UserType.MUSICIAN,
            PhotoUrlItem(
                "111",
                "https://sun1-88.userapi.com/impg/SsYpAAyxKG2SXIKXfY8iBvf2BTxZH9XYP2PFmA/lSVeMDXQuDM.jpg?size=1435x1435&quality=95&sign=c2dff2cc261588cb4a12c853c116199&type=album"
            ),
            PhotoUrlItem(
                "112",
                "https://i.pinimg.com/originals/06/67/9c/06679c2e2ae5aee8cf25eedc4bb41b98.jpg"
            ),
            UserSettingsItem(true, true)
        )
        // When
        bandRepository.addBandItem(bandItem)
        bandRepository.addBandMemberItem(bandItem, userItem)

        // Then
        val updatedBand = bandRepository.getBandItem("1")
        assertEquals(1, updatedBand.members.size)
        assertEquals(userItem, updatedBand.members.first())
    }

    @Test
    fun `test deleteBandItem`() = runBlocking {
        // Given
        val bandItem = BandItem(
            "1",
            "Bad Omens",
            listOf(
                UserItem(
                    "12",
                    "noahbadomens",
                    "Ной Себастьян",
                    "12341234",
                    "email@gmail.com",
                    "Вокалист",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "192",
                        "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
                    ),
                    PhotoUrlItem(
                        "1102",
                        "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                UserItem(
                    "13",
                    "nicholasriffruff",
                    "Николас Руффило",
                    "12341234",
                    "3@gmail.com",
                    "Гитарист группы Bad Omens",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "193",
                        "https://i.pinimg.com/originals/ed/bc/29/edbc295d751aa4ab31850fd052b7c37a.jpg"
                    ),
                    PhotoUrlItem(
                        "1103",
                        "https://images.squarespace-cdn.com/content/v1/54a06130e4b0510339b40c5e/1527895061651-7SU9556569GML2IQW2OI/BO-5.jpg"
                    ),
                    UserSettingsItem(true, true)
                )
            ),
            "Металкор",
            PhotoUrlItem(
                "791",
                "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
            )
        )
        bandRepository.addBandItem(bandItem)

        // When
        bandRepository.deleteBandItem(bandItem)

        // Then
        val bands = bandRepository.getBandsList().first()
        assertEquals(0, bands.size)
    }

    @Test
    fun `test editBandItem`() = runBlocking {
        // Given
        val originalBand = BandItem(
            "1",
            "Bad Omens",
            listOf(
                UserItem(
                    "12",
                    "noahbadomens",
                    "Ной Себастьян",
                    "12341234",
                    "email@gmail.com",
                    "Вокалист",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "192",
                        "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
                    ),
                    PhotoUrlItem(
                        "1102",
                        "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                UserItem(
                    "13",
                    "nicholasriffruff",
                    "Николас Руффило",
                    "12341234",
                    "3@gmail.com",
                    "Гитарист группы Bad Omens",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "193",
                        "https://i.pinimg.com/originals/ed/bc/29/edbc295d751aa4ab31850fd052b7c37a.jpg"
                    ),
                    PhotoUrlItem(
                        "1103",
                        "https://images.squarespace-cdn.com/content/v1/54a06130e4b0510339b40c5e/1527895061651-7SU9556569GML2IQW2OI/BO-5.jpg"
                    ),
                    UserSettingsItem(true, true)
                )
            ),
            "Металкор",
            PhotoUrlItem(
                "791",
                "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
            )
        )
        val updatedBand = BandItem(
            "1",
            "Bad Omens",
            listOf(
                UserItem(
                    "12",
                    "noahbadomens",
                    "Ной Себастьян",
                    "12341234",
                    "email@gmail.com",
                    "Вокалист",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "192",
                        "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
                    ),
                    PhotoUrlItem(
                        "1102",
                        "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                UserItem(
                    "13",
                    "nicholasriffruff",
                    "Николас Руффило",
                    "12341234",
                    "3@gmail.com",
                    "Гитарист группы Bad Omens",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "193",
                        "https://i.pinimg.com/originals/ed/bc/29/edbc295d751aa4ab31850fd052b7c37a.jpg"
                    ),
                    PhotoUrlItem(
                        "1103",
                        "https://images.squarespace-cdn.com/content/v1/54a06130e4b0510339b40c5e/1527895061651-7SU9556569GML2IQW2OI/BO-5.jpg"
                    ),
                    UserSettingsItem(true, true)
                )
            ),
            "Металкор",
            PhotoUrlItem(
                "791",
                "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
            )
        )
        bandRepository.addBandItem(originalBand)

        // When
        bandRepository.editBandItem(updatedBand)

        // Then
        val bands = bandRepository.getLocalBandsList()
        assertEquals(5, bands.size)
        assertEquals(updatedBand, bandRepository.getBandItem("1"))
    }

    @Test(expected = RuntimeException::class)
    fun `test getBandItem when band does not exist`() {
        // When
        bandRepository.getBandItem("1")
    }

    @Test
    fun `test getBandItem when band exists`() = runBlocking {
        // Given
        val bandItem = BandItem(
            "1",
            "Bad Omens",
            listOf(
                UserItem(
                    "12",
                    "noahbadomens",
                    "Ной Себастьян",
                    "12341234",
                    "email@gmail.com",
                    "Вокалист",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "192",
                        "https://i.pinimg.com/originals/7a/bd/00/7abd00f199dff4ec1364663ce0b45ea3.jpg"
                    ),
                    PhotoUrlItem(
                        "1102",
                        "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
                    ),
                    UserSettingsItem(true, true)
                ),
                UserItem(
                    "13",
                    "nicholasriffruff",
                    "Николас Руффило",
                    "12341234",
                    "3@gmail.com",
                    "Гитарист группы Bad Omens",
                    UserType.MUSICIAN,
                    PhotoUrlItem(
                        "193",
                        "https://i.pinimg.com/originals/ed/bc/29/edbc295d751aa4ab31850fd052b7c37a.jpg"
                    ),
                    PhotoUrlItem(
                        "1103",
                        "https://images.squarespace-cdn.com/content/v1/54a06130e4b0510339b40c5e/1527895061651-7SU9556569GML2IQW2OI/BO-5.jpg"
                    ),
                    UserSettingsItem(true, true)
                )
            ),
            "Металкор",
            PhotoUrlItem(
                "791",
                "https://chaoszine.net/wp-content/uploads/2023/11/bad-omens-2023.jpg"
            )
        )
        bandRepository.addBandItem(bandItem)

        // When
        val retrievedBand = bandRepository.getBandItem("1")

        // Then
        assertEquals(bandItem, retrievedBand)
    }
}
