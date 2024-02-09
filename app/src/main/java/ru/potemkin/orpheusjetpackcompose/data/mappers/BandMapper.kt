package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.BandDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateBandRequest
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateUserRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

class BandMapper {
    val userMapper = UsersMapper()
    fun mapBands(listBandDto: List<BandDto>): List<BandItem> {
        val result = mutableListOf<BandItem>()
        for (bandDto in listBandDto) {
            val bandItem = BandItem(
                id = bandDto.id,
                name = bandDto.name,
                members = userMapper.mapUsers(bandDto.members),
                genre = bandDto.genre,
                photo = mapPhotoUrlDtoToItem(bandDto.photo)
            )
            result.add(bandItem)
        }
        return result
    }
    fun mapBandToRequest(bandItem: BandItem): CreateBandRequest {
        return CreateBandRequest(
            name = bandItem.name,
            member = userMapper.mapUserDto(bandItem.members[0]),
            genre = bandItem.genre
        )
    }
    fun mapBand(bandDto: BandDto): BandItem {
        return BandItem(
            id = bandDto.id,
            name = bandDto.name,
            members = userMapper.mapUsers(bandDto.members),
            genre = bandDto.genre,
            photo = mapPhotoUrlDtoToItem(bandDto.photo)
        )
    }

    private fun mapPhotoUrlDtoToItem(photoUrlDto: PhotoUrlDto): PhotoUrlItem {
        return PhotoUrlItem(
            id = photoUrlDto.id,
            url = photoUrlDto.url
        )
    }
}
