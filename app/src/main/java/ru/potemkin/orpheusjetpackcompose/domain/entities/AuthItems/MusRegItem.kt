package ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusRegItem(
    val typeItem: TypeItem,
    val selectedInstrument:String,
    val selectedGenre:String,
):Parcelable