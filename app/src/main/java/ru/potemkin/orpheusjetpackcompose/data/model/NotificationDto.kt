package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class NotificationDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("contentDescription")
    val contentDescription: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("fromUser")
    val fromUser: UserDto,

    @SerializedName("toUser")
    val toUser: UserDto,

    @SerializedName("postItem")
    val postItem: PostDto?,

    @SerializedName("bandItem")
    val bandItem: BandDto?
)