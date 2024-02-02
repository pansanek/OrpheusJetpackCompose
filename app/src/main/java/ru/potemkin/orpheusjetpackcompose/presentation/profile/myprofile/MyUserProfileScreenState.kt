package ru.potemkin.orpheusjetpackcompose.presentation.profile.myprofile

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserItem

sealed class MyUserProfileScreenState {

    object Initial : MyUserProfileScreenState()

    object Loading : MyUserProfileScreenState()
    data class User(
        val user:  UserItem,
        val posts: List<PostItem>

    ) : MyUserProfileScreenState()
}