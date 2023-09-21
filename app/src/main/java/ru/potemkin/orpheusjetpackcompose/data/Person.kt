package ru.potemkin.orpheusjetpackcompose.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import ru.potemkin.orpheusjetpackcompose.R
import kotlinx.parcelize.Parcelize
@Parcelize
data class Person(
    val id: Int = 0,
    val name: String = "",
    @DrawableRes val icon: Int = R.drawable.ic_launcher_foreground
) : Parcelable

val personList = listOf(
    Person(
        1,
        "Лиза",
        R.drawable.sample1
    ),
    Person(
        2,
        "Антон",
        R.drawable.sample2
    ),
    Person(
        3,
        "Тамби",
        R.drawable.sample3
    ),
    Person(
        4,
        "Саша",
        R.drawable.sample4
    ),
    Person(
        5,
        "Макар",
        R.drawable.sample5
    ),
    Person(
        6,
        "Дима",
        R.drawable.sample6
    ),
    Person(
        7,
        "Арс",
        R.drawable.sample7
    ),
    Person(
        8,
        "Сережа",
        R.drawable.sample8
    )
)