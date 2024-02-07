package ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdmnRegItem (
    val typeItem: TypeItem,
    val address:String,
    val name:String,
    val desc:String,
):Parcelable