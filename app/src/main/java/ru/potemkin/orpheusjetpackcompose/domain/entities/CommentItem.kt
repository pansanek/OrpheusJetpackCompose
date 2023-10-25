package ru.potemkin.orpheusjetpackcompose.domain.entities

data class CommentItem(
    val commentId: Int,
    val userId: Int,
    val username: String,
    val text: String,
    val timestamp: Long
)