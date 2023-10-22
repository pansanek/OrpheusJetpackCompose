package ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.MessageRepository

class EditMessageUseCase(private val messageRepository: MessageRepository) {
    fun editMessageItem(messageItem: MessageItem){
        messageRepository.editMessageItem(messageItem)
    }
}