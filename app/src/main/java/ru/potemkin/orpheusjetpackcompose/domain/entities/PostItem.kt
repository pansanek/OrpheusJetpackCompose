package ru.potemkin.orpheusjetpackcompose.domain.entities

import androidx.annotation.DrawableRes
import ru.potemkin.orpheusjetpackcompose.R

data class PostItem(
    var id: String = UNDEFINED_ID,
    val userId: String,
    var text: String,
    val date: String,
    var likes: Int,
    var comments: List<CommentItem>,
    var attachments: List<String>,
    var creatorType: String
){
    companion object {
        const val UNDEFINED_ID = "0"
    }
}
