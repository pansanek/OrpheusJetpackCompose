package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserSettingsItem(
    val canReceiveMessagesForNewChats: Boolean,
    val canReceiveBandInvitations: Boolean
):Parcelable