package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.AdministratorDto
import ru.potemkin.orpheusjetpackcompose.domain.entities.AdministratorItem

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

    fun mapAdministrator(administratorDto: AdministratorDto): AdministratorItem {
        return AdministratorItem(
            id = administratorDto.id,
            user = userMapper.mapUser(administratorDto.user),
            locationId = administratorDto.locationId
        )
    }
}
