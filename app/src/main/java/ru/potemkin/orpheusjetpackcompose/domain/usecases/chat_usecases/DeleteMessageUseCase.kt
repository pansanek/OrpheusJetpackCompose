package ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.MessageRepository

class DeleteMessageUseCase (private val messageRepository: MessageRepository){
    fun deleteMessageItem(MessageItem: MessageItem){
        messageRepository.deleteMessageItem(MessageItem)
    }
}