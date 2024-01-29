package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatItem (
    var id: String = UNDEFINED_ID,
    var users: List<UserItem>,
    var lastMessage: String,
):Parcelable{
    companion object {
        const val UNDEFINED_ID = "0"

        val NavigationType: NavType<ChatItem> = object : NavType<ChatItem>(false) {

            override fun get(bundle: Bundle, key: String): ChatItem? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): ChatItem {
                return Gson().fromJson(value, ChatItem::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: ChatItem) {
                bundle.putParcelable(key, value)
            }
        }
    }
}