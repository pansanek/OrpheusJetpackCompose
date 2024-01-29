package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageItem(
    var id: String,
    var chat: ChatItem,
    var fromUser: UserItem,
    var timestamp: String,
    var content: String,
):Parcelable

