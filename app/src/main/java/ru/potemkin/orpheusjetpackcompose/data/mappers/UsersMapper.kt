package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserSettingsDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateUserRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserSettingsItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType

class UsersMapper {

    fun mapUsers(listUserDto: List<UserDto>): List<UserItem> {
        val result = mutableListOf<UserItem>()
        for (userDto in listUserDto) {
            result.add(mapUser(userDto))
        }
        return result
    }

    fun mapUser(userDto: UserDto): UserItem {
        return UserItem(
            id = userDto.id,
            login = userDto.login,
            name = userDto.name,
            password = userDto.password,
            email = userDto.email,
            about = userDto.about,
            user_type = UserType.valueOf(userDto.user_type),
            profile_picture = mapPhotoUrlDtoToItem(userDto.profile_picture),
            background_picture = mapPhotoUrlDtoToItem(userDto.background_picture),
            settings = mapUserSettingsDtoToItem(userDto.settings)
        )
    }

    fun mapUserToRequest(
        userItem: UserItem
    ): CreateUserRequest {
        return CreateUserRequest(
            login = userItem.login,
            name = userItem.name,
            password = userItem.password,
            email = userItem.email,
            about = userItem.about,
            userType = userItem.user_type.toString(),
            profile_picture = mapPhotoUrlItemToDto(userItem.profile_picture),
            background_picture = mapPhotoUrlItemToDto(userItem.background_picture),
            settings = mapUserSettingsItemToDto(userItem.settings)
        )
    }

    fun mapUserDto(userItem: UserItem): UserDto {
        return UserDto(
            id = userItem.id,
            about = userItem.about,
            email = userItem.email,
            background_picture = mapPhotoUrlItemToDto(userItem.background_picture),
            login = userItem.login,
            name = userItem.name,
            password = userItem.password,
            profile_picture = mapPhotoUrlItemToDto(userItem.profile_picture),
            user_type = userItem.user_type.toString(),
            settings = mapUserSettingsItemToDto(userItem.settings)
        )
    }

    private fun mapPhotoUrlItemToDto(photoUrlItem: PhotoUrlItem): PhotoUrlDto {
        return PhotoUrlDto(
            id = photoUrlItem.id,
            url = photoUrlItem.url
        )
    }

    private fun mapUserSettingsItemToDto(userSettingsItem: UserSettingsItem): UserSettingsDto {
        return UserSettingsDto(
            can_receive_messages_for_new_chats = userSettingsItem.canReceiveMessagesForNewChats,
            can_receive_band_invitations = userSettingsItem.canReceiveBandInvitations

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
