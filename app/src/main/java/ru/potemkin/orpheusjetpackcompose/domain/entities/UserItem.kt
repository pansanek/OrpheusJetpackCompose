package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import ru.potemkin.orpheusjetpackcompose.R

@Parcelize
data class UserItem(
    var id: String = UNDEFINED_ID,
    var login: String,
    var name:String,
    var password: String,
    var email: String,
    var about: String,
    var user_type: UserType,
    var profile_picture: PhotoUrlItem = PhotoUrlItem(
        UserItem.UNDEFINED_ID,
        "https://i.pinimg.com/originals/f0/e5/a9/f0e5a98f1731a3ca27dd942cf88d7ee8.jpg"
    ),
    var background_picture: PhotoUrlItem= PhotoUrlItem(
        UserItem.UNDEFINED_ID,
        "https://i.pinimg.com/originals/f0/e5/a9/f0e5a98f1731a3ca27dd942cf88d7ee8.jpg"
    ),
    var settings: UserSettingsItem = UserSettingsItem(
        canReceiveMessagesForNewChats = true,
        canReceiveBandInvitations = true
    )
):Parcelable {
    companion object {
        const val UNDEFINED_ID = "0"

        val NavigationType: NavType<UserItem> = object : NavType<UserItem>(false) {

            override fun get(bundle: Bundle, key: String): UserItem? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): UserItem {
                return Gson().fromJson(value, UserItem::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: UserItem) {
                bundle.putParcelable(key, value)
            }
        }
    }
}


