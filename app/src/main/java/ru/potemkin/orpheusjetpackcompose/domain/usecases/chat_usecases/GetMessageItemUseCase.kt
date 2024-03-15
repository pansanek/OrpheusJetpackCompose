package ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.ChatRepository
import javax.inject.Inject

class GetMessageItemUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    fun getMessageItem(messageId: String): MessageItem {
        return chatRepository.getMessageItem(messageId)
    }
}