package ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.MessageRepository

class GetMessageUseCase (private val messageRepository: MessageRepository) {
    fun getMessageItem(messageItemId: Int): MessageItem {
        return messageRepository.getMessageItem(messageItemId)
    }
}