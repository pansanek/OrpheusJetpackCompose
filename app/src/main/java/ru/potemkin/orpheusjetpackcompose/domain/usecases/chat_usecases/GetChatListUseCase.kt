package ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases

import kotlinx.coroutines.flow.StateFlow
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.ChatRepository
import javax.inject.Inject

class GetChatListUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    operator fun invoke(userId:String): StateFlow<List<ChatItem>> {
        return chatRepository.getChatList(userId)
    }
}