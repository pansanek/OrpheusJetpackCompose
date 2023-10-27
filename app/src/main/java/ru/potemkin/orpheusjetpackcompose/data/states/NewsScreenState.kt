package ru.potemkin.orpheusjetpackcompose.data.states

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

sealed class NewsScreenState {

    object Initial : NewsScreenState()
    data class Posts(
        val posts: List<PostItem>
    ) : NewsScreenState()
}