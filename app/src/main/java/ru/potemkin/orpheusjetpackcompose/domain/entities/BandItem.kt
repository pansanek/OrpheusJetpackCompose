package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class BandItem(
    var id: String = UNDEFINED_ID,
    val name: String,
    var members: List<UserItem>,
    val genre: String,
    val photo: PhotoUrlItem
): Parcelable{
    companion object {
        const val UNDEFINED_ID = "0"

        val NavigationType: NavType<BandItem> = object : NavType<BandItem>(false) {

            override fun get(bundle: Bundle, key: String): BandItem? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): BandItem {
                return Gson().fromJson(value, BandItem::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: BandItem) {
                bundle.putParcelable(key, value)
            }
        }
    }
}
