package ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusRegItem(
    val typeItem: TypeItem,
    val selectedInstrument:String,
    val selectedGenre:String,
):Parcelable{


    companion object {
        const val UNDEFINED_ID = "0"

        val NavigationType: NavType<MusRegItem> = object : NavType<MusRegItem>(false) {

            override fun get(bundle: Bundle, key: String): MusRegItem? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): MusRegItem {
                return Gson().fromJson(value, MusRegItem::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: MusRegItem) {
                bundle.putParcelable(key, value)
            }
        }
    }
}
