package ru.potemkin.orpheusjetpackcompose.data.states


import ru.potemkin.orpheusjetpackcompose.domain.entities.CommentItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem


data class CommentsViewState(
    val selectedPost: PostItem? = null,
    val isCommentDialogOpen: Boolean = false
)