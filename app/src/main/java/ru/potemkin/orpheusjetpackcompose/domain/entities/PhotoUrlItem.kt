package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoUrlItem(
    var id: String,
    var url: String
):Parcelable