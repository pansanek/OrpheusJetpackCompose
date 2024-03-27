package ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.ChatRepository
import javax.inject.Inject

class GetChatItemUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    operator fun invoke(chatId: String): ChatItem {
        return chatRepository.getChatItem(chatId)
    }
}