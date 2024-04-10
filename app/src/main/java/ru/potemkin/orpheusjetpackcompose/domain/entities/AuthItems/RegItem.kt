package ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize


@Parcelize
data class RegItem (
    val email: String,
    val username: String,
    val name:String,
    val password:String
):Parcelable
{


    companion object {
        const val UNDEFINED_ID = "0"

        val NavigationType: NavType<RegItem> = object : NavType<RegItem>(false) {

            override fun get(bundle: Bundle, key: String): RegItem? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): RegItem {
                return Gson().fromJson(value, RegItem::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: RegItem) {
                bundle.putParcelable(key, value)
            }
        }
    }
}
