package ru.potemkin.orpheusjetpackcompose.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers.UserProfileViewModel

@Module
interface UserProfileViewModelModule {

    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    @Binds
    fun bindUserProfileViewModel(viewModel: UserProfileViewModel): ViewModel
}