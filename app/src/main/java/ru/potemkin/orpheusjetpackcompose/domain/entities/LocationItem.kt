package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationItem(
    var id: String = UNDEFINED_ID,
    val admin: AdministratorItem,
    var name: String,
    var address: String,
    var about: String,
    var profilePicture: PhotoUrlItem
):Parcelable{
    companion object {
        const val UNDEFINED_ID = "0"

        val NavigationType: NavType<LocationItem> = object : NavType<LocationItem>(false) {

            override fun get(bundle: Bundle, key: String): LocationItem? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): LocationItem {
                return Gson().fromJson(value, LocationItem::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: LocationItem) {
                bundle.putParcelable(key, value)
            }
        }
    }
}
