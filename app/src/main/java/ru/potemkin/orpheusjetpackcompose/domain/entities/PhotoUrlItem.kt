package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoUrlItem(
    var id: Int,
    var url: String
):Parcelable