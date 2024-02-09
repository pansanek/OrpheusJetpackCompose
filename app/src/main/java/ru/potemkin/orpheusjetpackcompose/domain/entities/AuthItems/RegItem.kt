package ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegItem (
    val email: String,
    val username: String,
    val name:String,
    val password:String
):Parcelable