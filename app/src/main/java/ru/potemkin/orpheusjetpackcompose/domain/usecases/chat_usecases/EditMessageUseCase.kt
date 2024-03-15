package ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.ChatRepository
import javax.inject.Inject

class EditMessageUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    fun editMessageItem(messageItem: MessageItem){
        chatRepository.editMessageItem(messageItem)
    }
}