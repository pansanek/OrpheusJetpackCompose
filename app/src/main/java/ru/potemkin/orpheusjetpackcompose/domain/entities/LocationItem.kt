package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationItem(
    val name: String,
    val description: String,
    val address: String,
    @DrawableRes val photo: Int,
    val type: String,
    val administratorId: Int
): Parcelable {

}
