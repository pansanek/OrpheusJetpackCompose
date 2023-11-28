package ru.potemkin.orpheusjetpackcompose.domain.entities

import androidx.annotation.DrawableRes
import ru.potemkin.orpheusjetpackcompose.R

data class PostItem(
    var id: Int,
    val userId: Int,
    var text: String,
    val date: String,
    var likes: Int,
    var comments: List<CommentItem>,
    var attachments: List<String>
    //                @SerializedName("id") val id: Long,
//                @SerializedName("source_id") val userId: Long,
//                @SerializedName("text") val text: String,
//                @SerializedName("date") val date: Long,
//                @SerializedName("likes") val likes: LikesDto,
//                @SerializedName("comments") val comments: CommentsDto,
//                @SerializedName("attachments") val attachments: List<AttachmentDto>?

){
    companion object{
        const val UNDEFINED_ID = 0
    }
}
