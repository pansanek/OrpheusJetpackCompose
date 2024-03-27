package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostItem(
    var id: String = UNDEFINED_ID,
    val creatorId:String,
    val creatorName: String,
    val creatorPicture: PhotoUrlItem,
    var text: String,
    val date: String,
    var comments: List<CommentItem>,
    var attachment: PhotoUrlItem,
    var creatorType: CreatorType,
    var isLiked: Boolean,
    val statistics: List<StatisticItem>,
):Parcelable{
    companion object {
        const val UNDEFINED_ID = "0"

        val NavigationType: NavType<PostItem> = object : NavType<PostItem>(false) {

            override fun get(bundle: Bundle, key: String): PostItem? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): PostItem {
                return Gson().fromJson(value, PostItem::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: PostItem) {
                bundle.putParcelable(key, value)
            }
        }
    }
}


