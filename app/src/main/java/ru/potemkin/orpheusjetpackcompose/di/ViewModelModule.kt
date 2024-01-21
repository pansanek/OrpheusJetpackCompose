package ru.potemkin.orpheusjetpackcompose.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.potemkin.orpheusjetpackcompose.presentation.chat.ChatListViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.chat.ChatViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.map.MapViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.NewsViewModel


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(ChatListViewModel::class)
    @Binds
    fun bindChatListViewModel(viewModel: ChatListViewModel): ViewModel
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    @Binds
    fun bindChatViewModel(viewModel: ChatViewModel): ViewModel
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    @Binds
    fun bindMapViewModel(viewModel: MapViewModel): ViewModel
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    @Binds
    fun bindNewsViewModel(viewModel: NewsViewModel): ViewModel
}