package ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdmnRegItem (
    val typeItem: TypeItem,
    val address:String,
    val name:String,
    val desc:String,
):Parcelable{


    companion object {
        const val UNDEFINED_ID = "0"

        val NavigationType: NavType<AdmnRegItem> = object : NavType<AdmnRegItem>(false) {

            override fun get(bundle: Bundle, key: String): AdmnRegItem? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): AdmnRegItem {
                return Gson().fromJson(value, AdmnRegItem::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: AdmnRegItem) {
                bundle.putParcelable(key, value)
            }
        }
    }
}
