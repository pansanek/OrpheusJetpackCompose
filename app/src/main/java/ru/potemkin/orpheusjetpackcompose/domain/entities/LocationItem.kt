package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationItem(
    var id: Int = UNDEFINED_ID,
    var name: String,
    var description: String,
    val address: String,
    @DrawableRes val photo: Int,
    val type: String,
    var administratorId: Int
): Parcelable {

    companion object{
        const val UNDEFINED_ID = 0
    }
}
