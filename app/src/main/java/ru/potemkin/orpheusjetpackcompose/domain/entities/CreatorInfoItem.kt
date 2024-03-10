package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
class CreatorInfoItem(
    val creatorId: String,
    val creatorName: String,
    val creatorPicture: PhotoUrlItem,
    val creatorType: String
) : Parcelable {
    companion object {
        val NavigationType: NavType<CreatorInfoItem> = object : NavType<CreatorInfoItem>(false) {

            override fun get(bundle: Bundle, key: String): CreatorInfoItem? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): CreatorInfoItem {
                return Gson().fromJson(value, CreatorInfoItem::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: CreatorInfoItem) {
                bundle.putParcelable(key, value)
            }
        }
    }
}