package ru.potemkin.orpheusjetpackcompose.data.mappers

import android.util.Log
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserSettingsDto
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType

class UsersMapper {

    fun mapUsers(listUserDto: List<UserDto>): List<UserItem> {
        val result = mutableListOf<UserItem>()
        for (userDto in listUserDto) {
            val user = UserItem(
                id = userDto.id,
                login = userDto.login,
                password = userDto.password,
                email = userDto.email,
                about = userDto.about,
                user_type = UserType.valueOf(userDto.user_type),
                profile_picture = mapPhotoUrlDtoToItem(userDto.profile_picture),
                background_picture = mapPhotoUrlDtoToItem(userDto.background_picture),
                settings = mapUserSettingsDtoToItem(userDto.settings)
            )
            result.add(user)
        }
        return result
    }

    fun mapUser(userDto: UserDto): UserItem {
        return UserItem(
            id = userDto.id,
            login = userDto.login,
            password = userDto.password,
            email = userDto.email,
            about = userDto.about,
            user_type = UserType.valueOf(userDto.user_type),
            profile_picture = mapPhotoUrlDtoToItem(userDto.profile_picture),
            background_picture = mapPhotoUrlDtoToItem(userDto.background_picture),
            settings = mapUserSettingsDtoToItem(userDto.settings)
        )
    }

    private fun mapPhotoUrlDtoToItem(photoUrlDto: PhotoUrlDto): PhotoUrlItem {
        return PhotoUrlItem(
            id = photoUrlDto.id,
            url = photoUrlDto.url
        )
    }

    private fun mapUserSettingsDtoToItem(userSettingsDto: UserSettingsDto): UserSettingsItem {
        return UserSettingsItem(
            canReceiveMessagesForNewChats = userSettingsDto.can_receive_messages_for_new_chats,
            canReceiveBandInvitations = userSettingsDto.can_receive_band_invitations
        )
    }
}
