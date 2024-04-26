package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.BandDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateBandRequest
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateUserRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.BandItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

class BandMapper {
    var userMapper = UsersMapper()
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
            members = mapUserListToDtoList(bandItem.members),
            genre = bandItem.genre,
            photo = mapPhotoUrlItemToDto(bandItem.photo)
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
    private fun mapPhotoUrlItemToDto(photoUrlItem: PhotoUrlItem): PhotoUrlDto {
        return PhotoUrlDto(
            id = photoUrlItem.id,
            url = photoUrlItem.url
        )
    }

    private fun mapUserListToDtoList(users:List<UserItem>):List<UserDto>{
        val result = mutableListOf<UserDto>()
        for (user in users){
            result.add(userMapper.mapUserDto(user))
        }
        return result
    }
}
