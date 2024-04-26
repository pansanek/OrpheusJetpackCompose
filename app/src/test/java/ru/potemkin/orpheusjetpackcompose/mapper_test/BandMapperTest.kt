package ru.potemkin.orpheusjetpackcompose.mapper_test

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import ru.potemkin.orpheusjetpackcompose.data.mappers.BandMapper
import ru.potemkin.orpheusjetpackcompose.data.mappers.UsersMapper
import ru.potemkin.orpheusjetpackcompose.data.model.BandDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateBandRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem

class BandMapperTest {

    private lateinit var bandMapper: BandMapper
    private val userMapper: UsersMapper = mock()

    @Before
    fun setUp() {
        bandMapper = BandMapper()
        bandMapper.userMapper = userMapper
    }

    @Test
    fun `test mapBands`() {
        // Given
        val listBandDto = listOf(
            BandDto("1", "Band 1", emptyList(), "Rock", PhotoUrlDto("1", "photo_url")),
            BandDto("2", "Band 2", emptyList(), "Pop", PhotoUrlDto("2", "photo_url"))
        )

        whenever(userMapper.mapUsers(emptyList())).thenReturn(emptyList())

        // When
        val result = bandMapper.mapBands(listBandDto)

        // Then
        assertEquals(2, result.size)
        assertEquals("1", result[0].id)
        assertEquals("Band 1", result[0].name)
        assertEquals("Rock", result[0].genre)
        assertEquals(0, result[0].members.size)
        assertEquals("1", result[0].photo.id)
        assertEquals("photo_url", result[0].photo.url)
    }

    @Test
    fun `test mapBandToRequest`() {
        // Given
        val bandItem = BandItem(
            "1", "Band 1", emptyList(), "Rock",
            PhotoUrlItem("1", "photo_url")
        )

        val expectedRequest = CreateBandRequest(
            "Band 1", emptyList(), "Rock",
            PhotoUrlDto("1", "photo_url")
        )

        // When
        val result = bandMapper.mapBandToRequest(bandItem)

        // Then
        assertEquals(expectedRequest, result)
    }

    @Test
    fun `test mapBand`() {
        // Given
        val bandDto = BandDto(
            "1", "Band 1", emptyList(), "Rock",
            PhotoUrlDto("1", "photo_url")
        )

        whenever(userMapper.mapUsers(emptyList())).thenReturn(emptyList())

        // When
        val result = bandMapper.mapBand(bandDto)

        // Then
        assertEquals("1", result.id)
        assertEquals("Band 1", result.name)
        assertEquals("Rock", result.genre)
        assertEquals(0, result.members.size)
        assertEquals("1", result.photo.id)
        assertEquals("photo_url", result.photo.url)
    }
}
