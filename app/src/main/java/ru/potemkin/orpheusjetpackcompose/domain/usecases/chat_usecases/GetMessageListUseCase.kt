package ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.ChatRepository
import javax.inject.Inject

class GetMessageListUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    operator fun invoke(chatId: String): List<MessageItem>{
        return chatRepository.getMessageList(chatId)
    }
}