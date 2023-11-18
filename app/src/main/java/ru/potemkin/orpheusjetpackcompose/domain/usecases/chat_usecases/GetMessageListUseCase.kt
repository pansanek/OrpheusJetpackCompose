package ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.MessageRepository
import javax.inject.Inject

class GetMessageListUseCase @Inject constructor(private val messageRepository: MessageRepository) {
    fun getMessageList(): List<MessageItem>{
        return messageRepository.getMessagesList()
    }
}