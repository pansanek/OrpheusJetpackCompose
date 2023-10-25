package ru.potemkin.orpheusjetpackcompose.domain.entities

import androidx.annotation.DrawableRes
import ru.potemkin.orpheusjetpackcompose.R

data class PostItem(
    var id: Int,
    val userId: Int,
    val username: String,
    var caption: String,
    @DrawableRes val picture: Int = R.drawable.ic_launcher_foreground,
    val timestamp: Long,
    var likes: Int,
    var comments: List<CommentItem>
){
    companion object{
        const val UNDEFINED_ID = 0
    }
}
