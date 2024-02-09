package ru.potemkin.orpheusjetpackcompose.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.potemkin.orpheusjetpackcompose.presentation.auth.AuthViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.band.bandcreation.BandCreationViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.chat.chatlist.ChatListViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.main.MainViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.map.map.MapViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.NewsFeedViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile.MyUserProfileViewModel
import ru.potemkin.orpheusjetpackcompose.presentation.search.SearchViewModel


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(ChatListViewModel::class)
    @Binds
    fun bindChatListViewModel(viewModel: ChatListViewModel): ViewModel
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    @Binds
    fun bindMapViewModel(viewModel: MapViewModel): ViewModel
    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindNewsViewModel(viewModel: NewsFeedViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @Binds
    fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

    @IntoMap
    @ViewModelKey(BandCreationViewModel::class)
    @Binds
    fun bindBandCreationViewModel(viewModel: BandCreationViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MyUserProfileViewModel::class)
    @Binds
    fun bindMyUserProfileViewModel(viewModel: MyUserProfileViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    @Binds
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel
}