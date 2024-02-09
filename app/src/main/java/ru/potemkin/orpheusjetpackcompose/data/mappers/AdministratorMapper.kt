package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.AdministratorDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateAdministratorRequest
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateMusicianRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.AdministratorItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.LocationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MusicianItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

class AdministratorMapper {

    val userMapper = UsersMapper()
    fun mapAdministrators(listAdministratorDto: List<AdministratorDto>): List<AdministratorItem> {
        val result = mutableListOf<AdministratorItem>()
        for (administratorDto in listAdministratorDto) {
            val administrator = mapAdministrator(administratorDto)
            result.add(administrator)
        }
        return result
    }

//    fun mapAdministratorToRequest(userItem:UserItem): CreateAdministratorRequest {
//        return CreateAdministratorRequest(
//            user = userMapper.mapUserDto(userItem),
//
//        )
//    }

    fun mapAdministratorDto(adminItem:AdministratorItem): AdministratorDto {
        return AdministratorDto(
            id = adminItem.id,
            user = userMapper.mapUserDto(adminItem.user),
            locationId = adminItem.locationId,
        )
    }

    fun mapAdministrator(administratorDto: AdministratorDto): AdministratorItem {
        return AdministratorItem(
            id = administratorDto.id,
            user = userMapper.mapUser(administratorDto.user),
            locationId = administratorDto.locationId
        )
    }
}
