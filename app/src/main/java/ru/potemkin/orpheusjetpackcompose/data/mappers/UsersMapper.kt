package ru.potemkin.orpheusjetpackcompose.data.mappers

import android.util.Log
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

class UsersMapper {

    fun mapUsers(listUserDto: List<UserDto>): List<UserItem> {
        Log.d("USERSS", listUserDto.toString())
        val result = mutableListOf<UserItem>()
        for (userDto in listUserDto) {
            Log.d("USERSS","Photo ${userDto.photo}")
            Log.d("USERSS","Photo2 ${userDto.photo.url}")

            val user = UserItem(
                id = userDto.id,
                name = userDto.name,
                icon = userDto.photo.url,
                about = userDto.about,
                email = userDto.email,
                login = userDto.login
            )
            result.add(user)
        }
        return result
    }

    fun mapUser(userDto: UserDto): UserItem {
        val user = UserItem(
            id = userDto.id,
            name = userDto.name,
            icon = userDto.photo.url,
            about = userDto.about,
            email = userDto.email,
            login = userDto.login

        )

        return user
    }
}