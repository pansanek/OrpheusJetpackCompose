package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.LocationDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateLocationRequest
import ru.potemkin.orpheusjetpackcompose.data.network.ApiFactory.createGeocodingService
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem

class LocationMapper {
    val adminMapper = UsersMapper()
    val apiKey = "AIzaSyBofirSsbST-S3cGV-Gcnn80dEUx-5C2BU"
    suspend fun mapLocations(listLocationDto: List<LocationDto>): List<LocationItem> {
        val result = mutableListOf<LocationItem>()
        for (locationDto in listLocationDto) {
            val locationItem = getLongitudeFromAddress(locationDto.address,apiKey)?.let {
                getLatitudeFromAddress(locationDto.address,apiKey)?.let { it1 ->
                    LocationItem(
                        id = locationDto.id,
                        admin = adminMapper.mapUser(locationDto.admin),
                        name = locationDto.name,
                        address = locationDto.address,
                        about = locationDto.about,
                        profilePicture = mapPhotoUrlDtoToItem(locationDto.profilePicture),
                        longitude = it,
                        latitude = it1,
                    )
                }
            }
            if (locationItem != null) {
                result.add(locationItem)
            }
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

    suspend fun getLatitudeFromAddress(address: String, apiKey: String): Double? {
        val service = createGeocodingService()
        val response = service.getCoordinatesFromAddress(address, apiKey)
        return response.results.firstOrNull()?.geometry?.location?.lat
    }

    suspend fun getLongitudeFromAddress(address: String, apiKey: String): Double? {
        val service = createGeocodingService()
        val response = service.getCoordinatesFromAddress(address, apiKey)
        return response.results.firstOrNull()?.geometry?.location?.lng
    }
}
