package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments

import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.news.NewsFeedScreenState

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()
    object Loading : CommentsScreenState()

    data class Comments(
        val comments: List<CommentItem>
    ) : CommentsScreenState()
}