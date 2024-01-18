package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Parcelable
import androidx.annotation.DrawableRes
import ru.potemkin.orpheusjetpackcompose.R
import kotlinx.parcelize.Parcelize
@Parcelize
data class UserItem(
    var id: Int = UNDEFINED_ID,
    val login: String = "",
    val icon: String = "",
    val password:String = "",
    val name:String = "",
    val email:String="",
    val about: String = "",
    val userType:String = ""

) : Parcelable{
    companion object{
        const val UNDEFINED_ID = 0
    }
}

