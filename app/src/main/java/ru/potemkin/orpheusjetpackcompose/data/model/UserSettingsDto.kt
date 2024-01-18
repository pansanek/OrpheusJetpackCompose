package ru.potemkin.orpheusjetpackcompose.data.model

import com.google.gson.annotations.SerializedName

data class UserSettingsDto(
    @SerializedName("can_receive_messages_for_new_chats") val can_receive_messages_for_new_chats: Boolean,
    @SerializedName("can_receive_band_invitations") val can_receive_band_invitations: Boolean
)
