package ru.potemkin.orpheusjetpackcompose.data.mappers

import android.util.Log
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

class UsersMapper {

    fun mapUsers(listUserDto: List<UserDto>):List<UserItem>{
        Log.d("USERS",listUserDto.toString())
        val result = mutableListOf<UserItem>()

        for (userDto in listUserDto){
            val user =  UserItem(
                id = userDto.id,
                name = userDto.name
            )
            result.add(user)
        }
        return result
    }
}