package ru.potemkin.orpheusjetpackcompose.presentation.profile

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

sealed class ProfileScreenState {

    object Initial : ProfileScreenState()
    data class Posts(
        val posts: List<PostItem>
    ) : ProfileScreenState()
}