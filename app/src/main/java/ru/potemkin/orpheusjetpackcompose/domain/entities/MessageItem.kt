package ru.potemkin.orpheusjetpackcompose.domain.entities

import com.google.gson.annotations.SerializedName

data class MessageItem(
    var id: String,
    var chat: ChatItem,
    var fromUser: UserItem,
    var timestamp: String,
    var content: String,
)

