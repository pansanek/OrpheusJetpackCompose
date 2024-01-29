package ru.potemkin.orpheusjetpackcompose.domain.entities

import com.google.gson.annotations.SerializedName

data class ChatItem (
    var id: String,
    var users: List<UserItem>,
    var lastMessage: String,
)