package ru.potemkin.orpheusjetpackcompose.domain.entities

data class UserSettingsItem(
    val canReceiveMessagesForNewChats: Boolean,
    val canReceiveBandInvitations: Boolean
)