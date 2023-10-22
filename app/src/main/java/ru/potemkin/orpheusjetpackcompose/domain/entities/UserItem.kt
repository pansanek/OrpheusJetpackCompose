package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Parcelable
import androidx.annotation.DrawableRes
import ru.potemkin.orpheusjetpackcompose.R
import kotlinx.parcelize.Parcelize
@Parcelize
data class UserItem(
    var id: Int = UNDEFINED_ID,
    val name: String = "",
    @DrawableRes val icon: Int = R.drawable.ic_launcher_foreground
) : Parcelable{
    companion object{
        const val UNDEFINED_ID = 0
    }
}

