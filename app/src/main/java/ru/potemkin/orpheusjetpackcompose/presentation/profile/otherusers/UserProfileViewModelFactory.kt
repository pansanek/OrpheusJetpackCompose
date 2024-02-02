package ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

class UserProfileViewModelFactory(
    private val user: UserItem,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserProfileViewModel(user) as T
    }
}