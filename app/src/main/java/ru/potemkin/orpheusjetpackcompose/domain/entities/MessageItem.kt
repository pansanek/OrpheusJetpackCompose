package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MessageItem(
    var id: String,
    var chatId: String,
    var fromUser: UserItem,
    var timestamp: String,
    var content: String,
):Parcelable

