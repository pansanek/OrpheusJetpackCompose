package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.LocationDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateLocationRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem

class LocationMapper {
    val adminMapper = UsersMapper()
    fun mapLocations(listLocationDto: List<LocationDto>): List<LocationItem> {
        val result = mutableListOf<LocationItem>()
        for (locationDto in listLocationDto) {
            val locationItem = LocationItem(
                id = locationDto.id,
                admin = adminMapper.mapUser(locationDto.admin),
                name = locationDto.name,
                address = locationDto.address,
                about = locationDto.about,
                profilePicture = mapPhotoUrlDtoToItem(locationDto.profilePicture)
            )
            result.add(locationItem)
        }
        return result
    }

    fun mapLocationToRequest(
        userItem: UserItem,
        name: String,
        address: String,
        about: String
    ): CreateLocationRequest {
        return CreateLocationRequest(
            user = adminMapper.mapUserDto(userItem),
            name = name,
            address = address,
            about = about
        )
    }

    fun mapLocation(locationDto: LocationDto): LocationItem {
        return LocationItem(
            id = locationDto.id,
            admin = adminMapper.mapUser(locationDto.admin),
            name = locationDto.name,
            address = locationDto.address,
            about = locationDto.about,
            profilePicture = mapPhotoUrlDtoToItem(locationDto.profilePicture)
        )
    }

    private fun mapPhotoUrlDtoToItem(photoUrlDto: PhotoUrlDto): PhotoUrlItem {
        return PhotoUrlItem(
            id = photoUrlDto.id,
            url = photoUrlDto.url
        )
    }
}
