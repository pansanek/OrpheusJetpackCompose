package ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.MessageRepository

class AddMessageUseCase(private val messageRepository: MessageRepository) {
    fun addMessageItem(messageItem: MessageItem){
        messageRepository.addMessageItem(messageItem);
    }
}