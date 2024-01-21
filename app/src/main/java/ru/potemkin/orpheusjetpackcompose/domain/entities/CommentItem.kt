package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentItem(
    var id: String,
    var postId: String,
    var userId: String,
    var text: String,
    var timestamp: String
):Parcelable