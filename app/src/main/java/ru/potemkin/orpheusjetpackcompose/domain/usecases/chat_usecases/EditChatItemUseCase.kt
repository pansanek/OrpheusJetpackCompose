package ru.potemkin.orpheusjetpackcompose.domain.usecases.chat_usecases

import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.MessageItem
import ru.potemkin.orpheusjetpackcompose.domain.repositories.ChatRepository
import ru.potemkin.orpheusjetpackcompose.presentation.main.NavigationItem
import javax.inject.Inject

class EditChatItemUseCase @Inject constructor(private val chatRepository: ChatRepository) {
    operator fun invoke(chat: ChatItem) {
        return chatRepository.editChatItem(chat)
    }
}