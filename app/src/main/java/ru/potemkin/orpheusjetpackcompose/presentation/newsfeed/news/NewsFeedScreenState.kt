package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news

import ru.potemkin.orpheusjetpackcompose.domain.entities.NotificationItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    object Loading : NewsFeedScreenState()
    data class Posts(
        val posts: List<PostItem>,
        val notifications: List<NotificationItem>,
        val nextDataIsLoading: Boolean = false
    ) : NewsFeedScreenState()
}