package ru.potemkin.orpheusjetpackcompose.domain.entities


data class CommentItem(
    val id: Int,
    val authorId: Int,
    val text: String,
    val date: String
)