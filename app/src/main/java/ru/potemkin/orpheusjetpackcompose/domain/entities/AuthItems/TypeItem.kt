package ru.potemkin.orpheusjetpackcompose.domain.entities.AuthItems

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import ru.potemkin.orpheusjetpackcompose.domain.entities.PostItem
import ru.potemkin.orpheusjetpackcompose.domain.entities.UserType

@Parcelize
data class TypeItem (
    val aboutMeItem: AboutMeItem,
    val userType: UserType
):Parcelable{


    companion object {
        const val UNDEFINED_ID = "0"

        val NavigationType: NavType<TypeItem> = object : NavType<TypeItem>(false) {

            override fun get(bundle: Bundle, key: String): TypeItem? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): TypeItem {
                return Gson().fromJson(value, TypeItem::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: TypeItem) {
                bundle.putParcelable(key, value)
            }
        }
    }
}
