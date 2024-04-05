package ru.potemkin.orpheusjetpackcompose.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatisticItem(
    val type: StatisticType,
    var count: Int = 0
) : Parcelable

enum class StatisticType {
     COMMENTS, LIKES
}