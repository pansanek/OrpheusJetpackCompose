package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.ChatDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateChatRequest
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateUserRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

class ChatMapper {
    val userMapper = UsersMapper()
    fun mapChatList(listChatDto: List<ChatDto>): List<ChatItem> {
        val result = mutableListOf<ChatItem>()
        for (chatDto in listChatDto) {
            val chatItem = ChatItem(
                id = chatDto.id,
                users = userMapper.mapUsers(chatDto.users),
                lastMessage = chatDto.lastMessage,
                picture = PhotoUrlItem(
                    "",""
                ),
                name = ""
            )
            result.add(chatItem)
        }
        return result
    }
    fun mapChatToRequest(user:UserItem,secondUser:UserItem): CreateChatRequest {
        return CreateChatRequest(
            user = userMapper.mapUserDto(user),
            secondUser = userMapper.mapUserDto(secondUser),

        )
    }
    fun mapChat(chatDto: ChatDto): ChatItem {
        return ChatItem(
            id = chatDto.id,
            users = userMapper.mapUsers(chatDto.users),
            lastMessage = chatDto.lastMessage,
            picture = PhotoUrlItem(
                "",""
            ),
            name = ""
        )
    }

}
