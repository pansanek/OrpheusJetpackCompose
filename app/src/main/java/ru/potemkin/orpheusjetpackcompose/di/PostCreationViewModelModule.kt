package ru.potemkin.orpheusjetpackcompose.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

import ru.potemkin.orpheusjetpackcompose.presentation.post.PostCreationViewModel

@Module
interface PostCreationViewModelModule {

    @IntoMap
    @ViewModelKey(PostCreationViewModel::class)
    @Binds
    fun bindPostCreationViewModel(viewModel: PostCreationViewModel): ViewModel
}