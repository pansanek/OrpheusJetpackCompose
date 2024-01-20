package ru.potemkin.orpheusjetpackcompose.presentation.screens.states

import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    data class Comments(
        val feedPost: PostItem,
        val comments: List<CommentItem>
    ) : CommentsScreenState()
}