package ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType

@Parcelize
data class TypeItem (
    val aboutMeItem: AboutMeItem,
    val userType: UserType
):Parcelable