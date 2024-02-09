package ru.potemkin.orpheusjetpackcompose.data.mappers

import ru.potemkin.orpheusjetpackcompose.data.model.MessageDto
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateMessageRequest
import ru.potemkin.orpheusjetpackcompose.data.model.create_requests.CreateUserRequest
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

class MessageMapper {
    val userMapper = UsersMapper()
    fun mapMessageList(listMessageDto: List<MessageDto>): List<MessageItem> {
        val result = mutableListOf<MessageItem>()
        for (chatDto in listMessageDto) {
            val chatItem = MessageItem(
                id = chatDto.id,
                chatId = chatDto.chatId,
                fromUser = userMapper.mapUser(chatDto.fromUser),
                content = chatDto.content,
                timestamp = chatDto.timestamp
            )
            result.add(chatItem)
        }
        return result
    }
    fun mapMessageToRequest(messageItem: MessageItem): CreateMessageRequest {
        return CreateMessageRequest(
            chat_id= messageItem.chatId,
            from_user= userMapper.mapUserDto(messageItem.fromUser),
            content = messageItem.content
        )
    }
    fun mapMessage(chatDto: MessageDto): MessageItem {
        return MessageItem(
            id = chatDto.id,
            chatId = chatDto.chatId,
            fromUser = userMapper.mapUser(chatDto.fromUser),
            content = chatDto.content,
            timestamp = chatDto.timestamp
        )
    }

}
