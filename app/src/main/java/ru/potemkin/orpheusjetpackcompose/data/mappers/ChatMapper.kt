package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.ChatDto
import ru.potemkin.orpheusjetpackcompose.data.model.PhotoUrlDto
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PhotoUrlItem

class ChatMapper {
    val userMapper = UsersMapper()
    fun mapChatList(listChatDto: List<ChatDto>): List<ChatItem> {
        val result = mutableListOf<ChatItem>()
        for (chatDto in listChatDto) {
            val chatItem = ChatItem(
                id = chatDto.id,
                users = userMapper.mapUsers(chatDto.users),
                lastMessage = chatDto.lastMessage
            )
            result.add(chatItem)
        }
        return result
    }

    fun mapChat(chatDto: ChatDto): ChatItem {
        return ChatItem(
            id = chatDto.id,
            users = userMapper.mapUsers(chatDto.users),
            lastMessage = chatDto.lastMessage
        )
    }

}
