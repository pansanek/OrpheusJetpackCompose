package ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem
import ru.potemkin.orpheusjetpackcompose.domain.usecases.band_usecases.GetUserBandsUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.location_usecases.GetUserLocationUseCase
import ru.potemkin.orpheusjetpackcompose.domain.usecases.post_usecases.GetUserPostsUseCase

class UserProfileViewModelFactory(
    private val user: UserItem,
    private val getUserPostsUseCase: GetUserPostsUseCase,
    private val getUserBandsUseCase: GetUserBandsUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserProfileViewModel(user) as T
    }
}