package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class PostItem(
    var id: String = UNDEFINED_ID,
    val creatorId:String,
    var creatorName: String,
    var creatorPicture: PhotoUrlItem,
    var text: String,
    val date: String,
    var comments: List<CommentItem>,
    var attachment: PhotoUrlItem,
    var creatorType: CreatorType,
    var statistics: List<StatisticItem>,
    var isLiked:Boolean = false
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


