package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Parcelable
import androidx.annotation.DrawableRes
import ru.potemkin.orpheusjetpackcompose.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserItem(
    var id: String = UNDEFINED_ID,
    var login: String,
    var password: String,
    var email: String,
    var about: String,
    var user_type: UserType,
    var profile_picture: PhotoUrlItem,
    var background_picture: PhotoUrlItem,
    var settings: UserSettingsItem
):Parcelable {
    companion object {
        const val UNDEFINED_ID = "0"
    }
}


