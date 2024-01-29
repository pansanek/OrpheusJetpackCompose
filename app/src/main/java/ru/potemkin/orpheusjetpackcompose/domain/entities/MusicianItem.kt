package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicianItem(
    var id: String,
    val user: UserItem,
    var genre: String,
    var instrument: String
):Parcelable
