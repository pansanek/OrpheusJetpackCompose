package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.ChatDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.data.model.UserDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateChatRequest
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateUserRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

class ChatMapper {
    var userMapper = UsersMapper()
    fun mapChatList(listChatDto: List<ChatDto>): List<ChatItem> {
        val result = mutableListOf<ChatItem>()
        for (chatDto in listChatDto) {
            result.add(mapChat(chatDto))
        }
        return result
    }
    fun mapChatToRequest(chatItem: ChatItem): CreateChatRequest {
        return CreateChatRequest(
            users = mapUserListToDtoList(chatItem.users),
            lastMessage = chatItem.lastMessage,
            picture = mapPhotoUrlItemToDto(chatItem.picture),
            name= chatItem.name

        )
    }
    fun mapChat(chatDto: ChatDto): ChatItem {
        return ChatItem(
            id = chatDto.id,
            users = userMapper.mapUsers(chatDto.users),
            lastMessage = chatDto.lastMessage,
            picture = mapPhotoUrlDtoToItem(chatDto.picture),
            name = chatDto.name
        )
    }
    private fun mapUserListToDtoList(users:List<UserItem>):List<UserDto>{
        val result = mutableListOf<UserDto>()
        for (user in users){
            result.add(userMapper.mapUserDto(user))
        }
        return result
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
}
