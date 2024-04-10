package ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem

@Parcelize
data class AboutMeItem (
    val regItem:RegItem,
    val aboutMe:String,
):Parcelable
{


    companion object {
        const val UNDEFINED_ID = "0"

        val NavigationType: NavType<AboutMeItem> = object : NavType<AboutMeItem>(false) {

            override fun get(bundle: Bundle, key: String): AboutMeItem? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): AboutMeItem {
                return Gson().fromJson(value, AboutMeItem::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: AboutMeItem) {
                bundle.putParcelable(key, value)
            }
        }
    }
}
