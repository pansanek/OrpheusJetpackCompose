package ru.potemkin.orpheusjetpackcompose.domain.entities


data class CommentItem(
    var id: String,
    var postId: String,
    var userId: String,
    var text: String,
    var timestamp: String
)