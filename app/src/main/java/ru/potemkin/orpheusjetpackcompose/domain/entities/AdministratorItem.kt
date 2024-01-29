package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdministratorItem(
    val id: String,
    val user: UserItem,
    val locationId: String
):Parcelable
