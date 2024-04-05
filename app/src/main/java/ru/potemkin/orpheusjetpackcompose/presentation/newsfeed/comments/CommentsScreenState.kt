package ru.potemkin.orpheusjetpackcompose.presentation.newsfeed.comments

import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(
        val comments: List<CommentItem>
    ) : CommentsScreenState()
}