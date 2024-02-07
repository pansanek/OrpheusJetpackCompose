package ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AboutMeItem (
    val regItem:RegItem,
    val aboutMe:String,
):Parcelable