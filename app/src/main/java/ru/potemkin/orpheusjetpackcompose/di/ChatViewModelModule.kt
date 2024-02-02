package ru.potemkin.orpheusjetpackcompose.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.potemkin.orpheusjetpackcompose.presentation.chat.ChatViewModel

@Module
interface ChatViewModelModule {

    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    @Binds
    fun bindChatViewModel(viewModel: ChatViewModel): ViewModel
}