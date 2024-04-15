package ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.ChatRepository
import javax.inject.Inject

class GetMessageListUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    operator fun invoke(chatId: String): StateFlow<List<MessageItem>> {
        return chatRepository.getMessageList(chatId)
    }
}