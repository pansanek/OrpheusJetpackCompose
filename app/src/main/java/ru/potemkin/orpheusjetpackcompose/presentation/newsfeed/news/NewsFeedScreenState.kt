package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news

import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()
    data class Posts(
        val posts: List<PostItem>
    ) : NewsFeedScreenState()
}