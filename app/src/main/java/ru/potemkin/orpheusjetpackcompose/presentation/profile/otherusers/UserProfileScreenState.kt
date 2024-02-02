package ru.potemkin.orpheusjetpackcompose.presentation.profile.otherusers

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

sealed class UserProfileScreenState {

    object Initial : UserProfileScreenState()

    object Loading : UserProfileScreenState()
    data class User(
        val user:  UserItem,
        val posts: List<PostItem>

    ) : UserProfileScreenState()
}