package ru.potemkin.orpheusjetpackcompose.presentation.chat

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.potemkin.orpheusjetpackcompose.domain.entities.ChatItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

class ChatViewModelFactory(
    private val chatItem: ChatItem,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(chatItem) as T
    }
}